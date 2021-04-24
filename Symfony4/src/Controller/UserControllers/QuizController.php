<?php

namespace App\Controller\UserControllers;

use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuizFormType;
use App\Repository\QuestionRepository;
use App\Repository\QuizzRepository;
use Doctrine\ORM\EntityManagerInterface;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
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

    /**
     * @Route("/quiz/table", name="quiz_table")
     */
    public function listStudents(QuizzRepository $quizzRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $q = $request->query->get('q');
        $queryBuilder = $quizzRepository->getWithSearchQueryBuilder($q);

        $pagination = $paginator->paginate(
            $queryBuilder,
            $request->query->getInt('page', 1),
            5
        );

        return $this->render('user_controllers/quiz/table.html.twig', [
            'pagination' => $pagination,
        ]);
    }

    /**
     * @Route("/quiz/take/{id}", name="quiz_take")
     */
    public function takeQuiz($id, EntityManagerInterface $em)
    {
        $quiz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);

        return $this->render('user_controllers/quiz/take.html.twig',[
            'quiz' => $quiz,
            'questions' => $quiz->getQuestions(),
        ]);
    }

    /**
     * @Route("/quiz//take/{id}/submit", name="quiz_submit", methods="POST")
     */
    public function submitQuiz($id, QuestionRepository $questionRepository,Request $request, EntityManagerInterface $em)
    {
        $selectedAnswers = $request->get('selectedAnswers');
        $questions = $request->get('questions');
        $maxScore = 20;
        $questionCount = 0;
        $score = 0;

        foreach($questions as $question){
            $questionChosen = $questionRepository->findOneBy(['id'=>$question]);

            foreach ($selectedAnswers as $answer){
                if ($questionChosen->getRightAnswer() == $answer)
                    $score++;
            }

            $questionCount++;
        }

        $convertedAverage = ($score*$maxScore)/$questionCount;


        Return new JsonResponse([
            'selectedAnswers' => $selectedAnswers,
            'questions' => $questions,
            'convertedAverage' => $convertedAverage,
        ]);

    }




}
