<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Feedback;
use App\Entity\Meet;
use App\Form\FeedbackType;
use App\Repository\FeedbackRepository;
use App\Repository\MeetRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("mobile/feedback")
 */
class MobileFeedbackController extends AbstractController
{

    /**
     * @Route("/", name="mobile_user_controllers_feedback_index", methods={"GET"})
     */
    public function index(FeedbackRepository $feedbackRepository): Response
    {
        return $this->render('user_controllers/feedback/index.html.twig', [
            'feedback' => $feedbackRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new/{id}", name="mobile_user_feedback_new", methods={"GET","POST"})
     */
    public function new(Request $request, Meet $meet): Response
    {
        $feedback = new Feedback();
            $feedback->setIdMeet($meet);
            $feedback->setDescription($request->get('description'));
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($feedback);
            $meet->setFeedback($feedback);
            $meet->setEtat(1);
            $meet->getDisponibilite()->setEtat(0);

            $entityManager->persist($meet);
            $entityManager->flush();
        return new JsonResponse("Feedback Created", 200);
    }

    /**
     * @Route("/{id}", name="mobile_user_controllers_feedback_show", methods={"GET"})
     */
    public function show(Feedback $feedback): Response
    {
        return $this->render('user_controllers/feedback/show.html.twig', [
            'feedback' => $feedback,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="mobile_user_controllers_feedback_edit", methods={"GET","POST"})
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
     * @Route("/{id}", name="mobile_user_controllers_feedback_delete", methods={"POST"})
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
