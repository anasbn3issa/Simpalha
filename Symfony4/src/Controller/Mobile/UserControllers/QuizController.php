<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Quizz;
use App\Entity\QuizzResult;
use App\Entity\Users;
use App\Form\QuizFormType;
use App\Repository\QuestionRepository;
use App\Repository\QuizzRepository;
use App\Repository\QuizzResultRepository;
use Doctrine\ORM\EntityManagerInterface;
use Doctrine\ORM\Query\AST\Join;
use Knp\Component\Pager\PaginatorInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/quiz",name="mobile_")
 */
class QuizController extends AbstractController
{
    /**
     * @Route("/list", name="quiz_list")
     */
    public function list(EntityManagerInterface $em, QuizzRepository $quizzRepository, Request $request, PaginatorInterface $paginator, SerializerInterface $serializer): Response
    {
        $q = $request->query->get('q');

//        $user = $em->getRepository(Users::class)->findOneBy(['id'=>$this->getUser()->getId()]);
        $user = $em->getRepository(Users::class)->findOneBy(['id'=>11]);

        $quizes = $quizzRepository->getHelperQuizListQueryBuilder($user,$q)->getQuery()->getResult();

        $json = $serializer->serialize($quizes, 'json',['groups'=>'quizz']);

        return new JsonResponse([
           'quizes' => $json
        ]);
    }

    /**
     * @Route("/create", name="quiz_create", methods={"POST","GET"})
     */
    public function new(Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $quiz = new Quizz();

        if($request->getMethod() == "POST") {

//            $user = $em->getRepository(Users::class)->findOneBy(['id'=>$this->getUser()->getId()]);
            $user = $em->getRepository(Users::class)->findOneBy(['id'=>11]);

            /** @var Quizz $quiz */

            $quiz = $request->getContent();
            $data=$serializer->deserialize($quiz,Quizz::class,'json');
            $data->setHelper($user);

            $em->persist($data);
            $em->flush();
        }

        $json = $serializer->serialize($quiz, 'json',['groups'=>'quizz']);

        return new JsonResponse([
           'quiz' => $json
        ]);
    }

    /**
     * @Route("/{id}/delete", name="quiz_delete")
     */
    public function delete($id,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Quizz::class);

        $quiz= $repo->findOneBy([
            'id' => $id
        ]);

        $em->remove($quiz);
        $em->flush();
    }

    /**
     * @Route("/{id}/edit", name="quiz_edit", methods={"POST","GET"})
     */
    public function edit($id, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Quizz::class);

        $quizz = $repo->findOneBy(['id'=>$id]);

        if($request->getMethod() == "POST") {

            $content = $request->getContent();

            $data = $serializer->deserialize($content,Quizz::class,'json', [AbstractNormalizer::OBJECT_TO_POPULATE => $quizz]);

//            dd($data);

            $em->persist($data);
            $em->flush();
        }

        $json = $serializer->serialize($quizz,'json',['groups'=>'quizz']);

        return new JsonResponse([
            'quiz'=>$json
        ]);
    }


    // TODO : HERE WSELT HERE


    /**
     * @Route("/table", name="quiz_table")
     */
    public function listStudents(QuizzRepository $quizzRepository, Request $request, SerializerInterface $serializer): Response
    {
        $q = $request->query->get('q');
        $quizList = $quizzRepository->getWithSearchQueryBuilder($q)->getQuery()->getResult();

        $json = $serializer->serialize($quizList,"json",['groups'=>'quizz']);

        return new JsonResponse([
            "quizList" =>$json
        ]);
    }

    /**
     * @Route("/take/{id}", name="quiz_take")
     */
    public function takeQuiz($id, EntityManagerInterface $em, SerializerInterface $serializer)
    {
        $quiz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);

        $jsonQuiz = $serializer->serialize($quiz,"json",['groups'=>'quizz']);
        $jsonQuestions = $serializer->serialize($quiz->getQuestions(),"json",['groups' => 'question']);

        return new JsonResponse([
            "quiz" =>$jsonQuiz,
            "questions" => $jsonQuestions
        ]);
    }

    /**
     * @Route("/take/{id}/submit", name="quiz_submit", methods="POST")
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
            dump($request);

            dump($this->getUser()->getId());
            $quizFound = $quizzResultRepository->checkIfPassedQuizInLast24Hours($quizz,$this->getUser()->getId());

            dump($quizFound);

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
//                $quizzResult->setResultDate(new \DateTime(sprintf('-%d days',rand(0,2020))));
                $quizzResult->setResult($convertedAverage);

                $quizzResult->setStudentId($this->getUser()->getId());

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
     * @Route("/{id}/progression", name="quiz_progression", methods={"POST","GET"})
     */
    public function progressionQuiz($id, QuizzRepository $quizzRepository,QuizzResultRepository $quizzResultRepository,Request $request,SerializerInterface $serializer)
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

            $resultsList = $quizzResultRepository->getAllBeforeSelectedTimeQueryBuilder($quiz, new \DateTime())->getQuery()->getResult();

            $jsonQuizResults = $serializer->serialize($resultsList, 'json', ['groups'=>'quizz_result']);
            $jsonQuiz = $serializer->serialize($quiz,'json', ['groups'=>'quizz']);

            return new JsonResponse([
                'quizResults' => $jsonQuizResults,
                'quiz' => $jsonQuiz,
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
