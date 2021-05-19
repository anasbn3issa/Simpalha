<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Disponibilite;
use App\Entity\Users;
use App\Form\DisponibiliteType;
use App\Repository\DisponibiliteRepository;
use App\Repository\UsersRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("mobile/disponibilite")
 */
class MobileDisponibiliteController extends AbstractController
{
    /**
     * @Route("/", name="helper_disponibilite_index", methods={"GET"})
     */
    public function index(DisponibiliteRepository $disponibiliteRepository, UsersRepository $userRepository): Response
    {
        $helper = $userRepository->findOneBy(['email'=>$this->getUser()->getUsername()]);
        return $this->render('user_controllers/disponibilite/index.html.twig', [
            'disponibilites' => $disponibiliteRepository->getHelperDispMeet($helper),
        ]);
    }

    /**
     * @Route("/helper/{id}", name="helper_disponibilite_index", methods={"GET"})
     */
    public function getdisps(Users $helper, DisponibiliteRepository $disponibiliteRepository, UsersRepository $userRepository, SerializerInterface $serializer): Response
    {
        $disps = $disponibiliteRepository->getDisp($helper)->getQuery()->getResult();
        $res = $serializer->serialize($disps, 'json', ['groups'=>'meet:new']);
        if($res!=null){
            return new JsonResponse(array(
                'status' => 'OK',
                'data' => $res),
                200);
        }
        return new JsonResponse(array(
            'status' => 'ERROR',
            'message'=>"fetch error"),
            500);
    }


    /**
     * @Route("/new", name="user_disponibilite_new", methods={"GET","POST"})
     */
    public function new(Request $request, UsersRepository $userRepository): Response
    {
        $disponibilite = new Disponibilite();

        $disponibilite->setHelperid($userRepository->findOneBy(['email'=>$this->getUser()->getUsername()]));

        $form = $this->createForm(DisponibiliteType::class, $disponibilite);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $disponibilite->setHelperid($userRepository->findOneBy(['email'=>$this->getUser()->getUsername()]));

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($disponibilite);
            $entityManager->flush();

            return $this->redirectToRoute('user_disponibilite_index');
        }

        return $this->render('user_controllers/disponibilite/new.html.twig', [
            'disponibilite' => $disponibilite,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_disponibilite_show", methods={"GET"})
     */
    public function show(Disponibilite $disponibilite): Response
    {
        return $this->render('user_controllers/disponibilite/show.html.twig', [
            'disponibilite' => $disponibilite,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="user_disponibilite_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Disponibilite $disponibilite): Response
    {
        $form = $this->createForm(DisponibiliteType::class, $disponibilite);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_controllers_disponibilite_index');
        }

        return $this->render('user_controllers/disponibilite/edit.html.twig', [
            'disponibilite' => $disponibilite,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_controllers_disponibilite_delete", methods={"POST"})
     */
    public function delete(Request $request, Disponibilite $disponibilite): Response
    {
        if ($this->isCsrfTokenValid('delete'.$disponibilite->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($disponibilite);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_controllers_disponibilite_index');
    }
}
