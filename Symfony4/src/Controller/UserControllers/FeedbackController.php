<?php

namespace App\Controller\UserControllers;

use App\Entity\Feedback;
use App\Form\FeedbackType;
use App\Repository\FeedbackRepository;
use App\Repository\MeetRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/feedback")
 */
class FeedbackController extends AbstractController
{

    /**
     * @Route("/", name="user_controllers_feedback_index", methods={"GET"})
     */
    public function index(FeedbackRepository $feedbackRepository): Response
    {
        return $this->render('user_controllers/feedback/index.html.twig', [
            'feedback' => $feedbackRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="user_feedback_new", methods={"GET","POST"})
     */
    public function new(Request $request, MeetRepository $meetRepository): Response
    {
        $feedback = new Feedback();
        $form = $this->createForm(FeedbackType::class, $feedback);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $meet = $meetRepository->findOneBy(['id'=>$_POST['idmt']]);
            $feedback->setIdMeet($meet);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($feedback);
            $meet->setFeedback($feedback);
            $meet->setEtat(1);
            $meet->getDisponibilite()->setEtat(0);

            $entityManager->persist($meet);
            $entityManager->flush();

            return $this->redirectToRoute('meet_index');
        }

        return $this->render('user_controllers/feedback/new.html.twig', [
            'feedback' => $feedback,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_controllers_feedback_show", methods={"GET"})
     */
    public function show(Feedback $feedback): Response
    {
        return $this->render('user_controllers/feedback/show.html.twig', [
            'feedback' => $feedback,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="user_controllers_feedback_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Feedback $feedback): Response
    {
        $form = $this->createForm(FeedbackType::class, $feedback);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_controllers_feedback_index');
        }

        return $this->render('user_controllers/feedback/edit.html.twig', [
            'feedback' => $feedback,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_controllers_feedback_delete", methods={"POST"})
     */
    public function delete(Request $request, Feedback $feedback): Response
    {
        if ($this->isCsrfTokenValid('delete'.$feedback->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($feedback);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_controllers_feedback_index');
    }
}
