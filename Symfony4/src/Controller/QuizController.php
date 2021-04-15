<?php

namespace App\Controller;

use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuizFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class QuizController extends AbstractController
{
    /**
     * @Route("/quiz", name="quiz")
     */
    public function index(): Response
    {
        return $this->render('user_controllers/quiz/index.html.twig', [
            'controller_name' => 'QuizController',
        ]);
    }

    /**
     * @Route("/quiz/list", name="quiz_list")
     */
    public function list(EntityManagerInterface $em): Response
    {
        $repository = $em->getRepository(Users::class);

        $user = $repository->findOneBy(
            ['id' => 14]
        );

        $quizes = $user->getQuizzs();

        return $this->render('user_controllers/quiz/list.html.twig', [
            'quizzs' => $quizes,
        ]);
    }

    /**
     * @Route("/quiz/create", name="quiz_create")
     */
    public function new(Request $request, EntityManagerInterface $em): Response
    {

        $quiz = new Quizz();

        // TODO : Make it modular once the login is implemented
        $user = $em->getRepository(Users::class)->findOneBy(['id'=>14]);

        $repo = $em->getRepository(Quizz::class);

        $form = $this->createForm(QuizFormType::class, $quiz);
        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {

            /** @var Quizz $quiz */

            $quiz = $form->getData();
            $quiz->setHelper($user);

            $em->persist($quiz);
            $em->flush();

            $this->addFlash('success','You successfully created a new Quiz');

            return $this->redirectToRoute('quiz_list');
        }

        return $this->render('user_controllers/quiz/new.html.twig', [
            'quizzForm' => $form->createView(),
        ]);
    }

    /**
     * @Route("/quizz/{id}/delete", name="quiz_delete")
     */
    public function delete($id,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Quizz::class);

        $quiz= $repo->findOneBy([
            'id' => $id
        ]);

        $title = $quiz->getTitle();

        $em->remove($quiz);
        $em->flush();

        $this->addFlash('failure','Quiz '.$title.' was deleted from your list..');

        return $this->redirectToRoute('quiz_list');
    }

    /**
     * @Route("/quiz/{id}/edit", name="quiz_edit")
     */
    public function edit($id, EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Quizz::class);

        $quizz = $repo->findOneBy(['id'=>$id]);

        $form = $this->createForm(QuizFormType::class, $quizz);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {

            $quizz = $form->getData();

            $em->persist($quizz);
            $em->flush();

            $this->addFlash('success','Quiz '.$quizz->getTitle().' updated! Knowledge is POWER!');

            return $this->redirectToRoute('quiz_list');
        }

        return $this->render('user_controllers/quiz/update.html.twig', [
            'quizForm' => $form->createView(),
        ]);
    }




}
