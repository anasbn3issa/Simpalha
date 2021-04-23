<?php

namespace App\Controller\AdminControllers;

use App\Entity\Candidatures;
use App\Entity\Users;
use App\Form\UserType;
use App\Repository\UsersRepository;
use Exception;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;

/**
 * @Route("/adminUser")
 */
class AdminUsersController extends AbstractController
{
    /**
     * @var UserPasswordEncoderInterface
     */
    private $passwordEncoder;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder)
    {
        $this->passwordEncoder = $passwordEncoder;
    }

    /**
     * @Route("/users", name="users_index", methods={"GET"})
     * @param UsersRepository $usersRepository
     * @return Response
     */
    public function index(UsersRepository $usersRepository): Response
    {
        if (!$this->isGranted('ROLE_ADMIN')) {
            return $this->redirect($this->generateUrl('homepage'));
        }
        return $this->render('user/admin/index.html.twig', [
            'users' => $usersRepository->findAll(),
        ]);
    }

    /**
     * @Route("/users/changeStatus/{id}", name="users_isActive")
     * @param int $id
     * @return RedirectResponse
     */
    public function userChangeIsActive(int $id): RedirectResponse
    {
        if (!$this->isGranted('ROLE_ADMIN')) {
            return $this->redirect($this->generateUrl('homepage'));
        }

        $em = $this->getDoctrine()->getManager();
        $users = $this->getDoctrine()->getRepository(Users::class)->find($id);
        $users->setIsActive(!$users->getIsActive() ) ;
        $em->persist($users);
        $em->flush();
        $this->addFlash('success', 'User Status changed successfully');
        return $this->redirect($this->generateUrl('users_index'));
    }


    /**
     * @Route("/new", name="users_new", methods={"GET","POST"})
     * @param Request $request
     * @return Response
     * @throws Exception
     */
    public function new(Request $request): Response
    {
        $user = new Users();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $user->setPassword($this->passwordEncoder->encodePassword($user, $form->get("Password")->getData()));
            /* $user->setToken($this->generateToken());*/
            $user->setRole('0');
            // $user->setRoles(['ROLE_USER']);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($user);
            $entityManager->flush();

            return $this->redirectToRoute('users_index');
        }

        return $this->render('user/admin/new.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }


    /**
     * @Route("/{id}", name="user_controllers_users_show", methods={"GET"})
     * @param Users $user
     * @return Response
     */
    public function show(Users $user): Response
    {
        return $this->render('user/admin/show.html.twig', [
            'user' => $user,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="user_controllers_users_edit", methods={"GET","POST"})
     * @param Request $request
     * @param Users $user
     * @return Response
     */
    public function edit(Request $request, Users $user): Response
    {
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('users_index');
        }

        return $this->render('user/admin/edit.html.twig', [
            'user' => $user,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_controllers_users_delete", methods={"POST"})
     * @param Request $request
     * @param Users $user
     * @return Response
     */
    public function delete(Request $request, Users $user): Response
    {
        if ($this->isCsrfTokenValid('delete' . $user->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($user);
            $entityManager->flush();
        }

        return $this->redirectToRoute('users_index');
    }


}
