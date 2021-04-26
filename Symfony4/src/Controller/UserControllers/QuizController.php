<?php

namespace App\Controller\UserControllers;

use App\Entity\Quizz;
use App\Entity\QuizzResult;
use App\Entity\Users;
use App\Form\QuizFormType;
use App\Repository\QuestionRepository;
use App\Repository\QuizzRepository;
use App\Repository\QuizzResultRepository;
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
    public function list(EntityManagerInterface $em, QuizzRepository $quizzRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $q = $request->query->get('q');

        $repository = $em->getRepository(Users::class);

        $user = $repository->findOneBy(
            ['id' => 14]
        );

        $queryBuilder = $quizzRepository->getHelperQuizListQueryBuilder($user,$q);


        $pagination = $paginator->paginate(
            $queryBuilder,
            $request->query->getInt('page', 1),
            5
        );

        return $this->render('user_controllers/quiz/list.html.twig', [
            'pagination' => $pagination,
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

            return $this->redirectToRoute('question_list',['id'=>$quiz->getId()]);
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
            
            return $this->redirectToRoute('question_list',['id'=>$quizz->getId()]);
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
     * @Route("/quiz/take/{id}/submit", name="quiz_submit", methods="POST")
     */
    public function submitQuiz($id, QuestionRepository $questionRepository,QuizzResultRepository $quizzResultRepository,Request $request, EntityManagerInterface $em)
    {
        $quizz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);
        $selectedAnswers = $request->get('selectedAnswers');
        $questions = $request->get('questions');
        $maxScore = 20;
        $questionCount = 0;
        $score = 0;

        $elementCount = count($questions);


        if($elementCount<=0){
            $convertedAverage = -1;
        }
        else {
            // TODO : CHANGE THE HARDCODED STUDENT ONCE THERE'S A LOGGED IN STUDENT
            $quizFound = $quizzResultRepository->checkIfPassedQuizInLast24Hours($quizz,14);

            if($quizFound){
                $convertedAverage = -2;
            }
            else{
                foreach($questions as $question){
                    $questionChosen = $questionRepository->findOneBy(['id'=>$question]);

                    foreach ($selectedAnswers as $answer){
                        if ($questionChosen->getRightAnswer() == $answer)
                            $score++;
                    }

                    $questionCount++;
                }

                $convertedAverage = ($score*$maxScore)/$questionCount;

                $quizzResult = new QuizzResult();

                $quizzResult->setQuizz($quizz);
                $quizzResult->setResultDate(new \DateTime());
//                $quizzResult->setResultDate(new \DateTime(sprintf('-%d months',rand(0,2020))));
                $quizzResult->setResult($convertedAverage);

                // TODO : CHANGE THE HARDCODED STUDENT ONCE THERE'S A LOGGED IN STUDENT
                $quizzResult->setStudentId(14);

                $em->persist($quizzResult);
                $em->flush();

            }

        }

        Return new JsonResponse([
            'selectedAnswers' => $selectedAnswers,
            'questions' => $questions,
            'convertedAverage' => $convertedAverage,
        ]);

    }

    /**
     * @Route("/quiz/{id}/progression", name="quiz_progression")
     */
    public function progressionQuiz($id, QuizzRepository $quizzRepository,QuizzResultRepository $quizzResultRepository,Request $request,PaginatorInterface $paginator, EntityManagerInterface $em)
    {

        if($request->isMethod('GET'))
        {
            $quiz = $quizzRepository->findOneBy(['id'=>$id]);
            $quizResults = $quizzResultRepository->findAllBeforeSelectedTime($quiz,new \DateTime());

            if($quizResults){
                $quizCount = 0;
                $quizScore = 0;

                foreach ($quizResults as $quizResult)
                {
                    $quizScore += $quizResult->getResult();
                    $quizCount++;
                }

                $average = round($quizScore / $quizCount,2);
            }
            else{
                $average = -1;
            }

            $queryBuilder = $quizzResultRepository->getAllBeforeSelectedTimeQueryBuilder($quiz, new \DateTime());

            $pagination = $paginator->paginate(
                $queryBuilder,
                $request->query->getInt('page', 1),
                5
            );

            return $this->render('user_controllers/quiz/progression.html.twig',[
                'pagination' => $pagination,
                'quiz' => $quiz,
                'average' => $average
            ]);
        }
        else if($request->isMethod('POST')){
            $quiz = $quizzRepository->findOneBy(['id'=>$id]);

            $date = $request->get('selectedDate');
            dump($date);

            $quizResults = $quizzResultRepository->findAllBeforeSelectedTime($quiz,$date);

            dump($quizResults);

            $quizResultsTotal = $quizzResultRepository->findAllBeforeSelectedTime($quiz,new \DateTime());

            if($quizResults){
                $quizCount = 0;
                $quizScore = 0;
                $quizCountTotal = 0;
                $quizScoreTotal = 0;

                foreach ($quizResults as $quizResult)
                {
                    $quizScore += $quizResult->getResult();
                    $quizCount++;
                }

                foreach ($quizResultsTotal as $quizResult)
                {
                    $quizScoreTotal += $quizResult->getResult();
                    $quizCountTotal++;
                }


                $average = round($quizScore / $quizCount,2);
                $averageTotal = round($quizScoreTotal / $quizCountTotal,2);
                $averageDiff = $averageTotal - $average;
            }
            else{
                $average = -1;
            }

            Return new JsonResponse([
                'averageTotal' => $averageTotal,
                'averageDiff' => $averageDiff,
                'average' => $average,
            ]);
        }


    }




}
