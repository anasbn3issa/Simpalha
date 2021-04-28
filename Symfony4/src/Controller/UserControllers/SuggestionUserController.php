<?php

namespace App\Controller\UserControllers;

use App\Entity\Suggestion;
use App\Form\SuggestionType;
use App\Repository\SuggestionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @Route("/user/controllers/suggestion/user")
 */
class SuggestionUserController extends AbstractController
{
    /**
     * @Route("/", name="user_controllers_suggestion_user_index", methods={"GET"})
     */
    public function index(SuggestionRepository $suggestionRepository): Response
    {
        return $this->render('user_controllers/suggestion_user/index.html.twig', [
            'suggestions' => $suggestionRepository->findAll(),
        ]);
    }

    /**
     * @Route("/newSuggUser", name="user_controllers_suggestion_user_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $suggestion = new Suggestion();
        $form = $this->createForm(SuggestionType::class, $suggestion);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($suggestion);
            $entityManager->flush();

            return $this->redirectToRoute('user_controllers_suggestion_user_index');
        }

        return $this->render('user_controllers/suggestion_user/new.html.twig', [
            'suggestion' => $suggestion,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idSugg}", name="user_controllers_suggestion_user_show", methods={"GET"})
     */
    public function show(Suggestion $suggestion): Response
    {
        return $this->render('user_controllers/suggestion_user/show.html.twig', [
            'suggestion' => $suggestion,
        ]);
    }


}
