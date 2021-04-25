<?php

namespace App\Controller\AdminControllers;

use App\Entity\Meet;
use App\Entity\Users;
use App\Form\MeetType;
use App\Repository\MeetRepository;
use App\Repository\UserRepository;
use Ramsey\Uuid\Uuid;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;

/**
 * @Route("/admin/meet")
 */
class AdminMeetController extends AbstractController
{
    /**
     * @Route("/", name="admin_meet", methods={"GET"})
     */
    public function index(MeetRepository $meetRepository): Response
    {
        return $this->render('admin_controllers/meet/index.html.twig', [
            'meets' => $meetRepository->findAll(),
        ]);
    }

    /**
     * @Route("/calendar", name="admin_calendar", methods={"GET"})
     */
    public function test(MeetRepository $meetRepository): Response
    {
        return $this->render('admin_controllers/meet/calendar.html.twig', [
            'meets' => $meetRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new/{id}", name="meet_new", methods={"GET","POST"})
     */
    public function new(Request $request, Users $user): Response
    {
        //$helperId = $request->query->get('user');
        //$helper = $userRepo->findOneBy(['id'=>$helperId]);
        $meet = new Meet();

        $meet->setIdHelper($user);
        $meet->setIdStudent($user);

        $meet->setSpecialite($user->getSpecialite());

        $form = $this->createForm(MeetType::class, $meet,array(
        ));
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            //updateDisponibilite
            $disponibilite = $form["disponibilite"]->getData();
            $disponibilite->setEtat(1);

            $entityManager->persist($meet);
            $entityManager->persist($disponibilite);

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
        $form = $this->createForm(MeetType::class, $meet);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
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
            $entityManager->remove($meet);
            $entityManager->flush();
        }

        return $this->redirectToRoute('meet_index');
    }


    /**
     * @Route("/search", name="admin_meet_search", methods={"GET"})
     */
    public function search(Request $request, MeetRepository $meetRepository, NormalizerInterface $normalizer): Response
    {
        $filter = $request->query->get('filter');
        $meets = $meetRepository->search($filter);
        if(sizeof($meets)>0) {
            $json_meets = $normalizer->normalize($meets, 'json', ['groups'=>'meet:search']);
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
}
