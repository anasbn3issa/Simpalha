<?php

namespace App\Controller\UserControllers;

use App\Entity\Feedback;
use App\Entity\Meet;
use App\Entity\Users;
use App\Form\FeedbackType;
use App\Form\MeetType;
use App\Repository\DisponibiliteRepository;
use App\Repository\MeetRepository;
use App\Repository\UserRepository;
use App\Repository\UsersRepository;
use Ramsey\Uuid\Uuid;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/meet")
 */
class MeetController extends AbstractController
{
    /**
     * @Route("/getmeetbydisp", name="getmeetbydisp", methods={"GET"})
     */
    public function getmeetbydisp(Request $request, MeetRepository $meetRepository, DisponibiliteRepository $disponibiliteRepository, NormalizerInterface $normalizer): Response
    {
        $filter = $request->query->get('id');
        $dis = $disponibiliteRepository->findOneBy(['id'=>$filter]);
        $meet = $meetRepository->findOneBy(['disponibilite'=>$dis]);
        if($meet != null) {
            $json_meets = $normalizer->normalize($meet, 'json', ['groups'=>'meet:search']);
            // Send all this back to client
            return new JsonResponse(array(
                'status' => 'OK',
                'data' => $json_meets),
                200);
        }
        return new JsonResponse(array(
            'status' => 'ERROR',
            'data' => 'not found'),
            200);
    }

    /**
     * @Route("/", name="meet_index", methods={"GET"})
     */
    public function index(MeetRepository $meetRepository): Response
    {
        if($this->getUser()!=null) {
            $feedback = new Feedback();
            $feedback_form = $this->createForm(FeedbackType::class, $feedback);
            $meetshlp=null;
            if ($this->isGranted('ROLE_HELPER')) {
                $meetshlp = $meetRepository->findBy(['idHelper'=>$this->getUser()]);
            }
            return $this->render('user_controllers/meet/index.html.twig', [
                'meets' => $meetRepository->findByUser($this->getUser()),
                'meets_hlp' => $meetshlp,
                'feedback'=>$feedback_form->createView(),
            ]);
        }
        return $this->redirectToRoute('security_login');
    }

    /**
     * @Route("/new/{id}", name="meet_new", methods={"GET","POST"})
     */
    public function new(Request $request, Users $user, UsersRepository $userRepository): Response
    {

        $meet = new Meet();

        $meet->setIdHelper($user);
        $meet->setIdStudent($userRepository->findOneBy(['email'=>$this->getUser()->getUsername()]));

        $meet->setSpecialite($user->getSpecialite());

        $form = $this->createForm(MeetType::class, $meet, [
            'disp'=>null,
            'helper'=>$user
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            //updateDisponibilite
            $disponibilite = $form["disponibilite"]->getData();
            $disponibilite->setEtat(1);

            $entityManager->persist($disponibilite);
            $entityManager->persist($meet);

            $entityManager->flush();


            return $this->redirectToRoute('meet_index');
        }

        return $this->render('user_controllers/meet/new.html.twig', [
            'meet' => $meet,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="meet_show", methods={"GET"})
     */
    public function show(Meet $meet): Response
    {
        return $this->render('user_controllers/meet/show.html.twig', [
            'meet' => $meet,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="meet_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Meet $meet): Response
    {
        $olddisp = $meet->getDisponibilite();
        $form = $this->createForm(MeetType::class, $meet, [
            'disp' => $meet->getDisponibilite(),
            'helper'=>$meet->getIdHelper()
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            $olddisp->setEtat(0);

            $disponibilite = $form["disponibilite"]->getData();
            $disponibilite->setEtat(1);

            $entityManager->persist($olddisp);
            $entityManager->persist($disponibilite);

            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('meet_index');
        }

        return $this->render('user_controllers/meet/edit.html.twig', [
            'meet' => $meet,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="meet_delete", methods={"POST"})
     */
    public function delete(Request $request, Meet $meet): Response
    {
        if ($this->isCsrfTokenValid('delete'.$meet->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();

            $disponibilite = $meet->getDisponibilite();
            $disponibilite->setEtat(0);

            $entityManager->persist($disponibilite);

            $entityManager->remove($meet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('meet_index');
    }
}
