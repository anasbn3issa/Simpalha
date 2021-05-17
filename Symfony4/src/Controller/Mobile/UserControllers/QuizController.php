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
     * @Route("/list/{uid}", name="quiz_list")
     */
    public function listJSON($uid,EntityManagerInterface $em, QuizzRepository $quizzRepository, Request $request, PaginatorInterface $paginator, SerializerInterface $serializer): Response
    {
        $q = $request->query->get('q');

//        $user = $em->getRepository(Users::class)->findOneBy(['id'=>$this->getUser()->getId()]);
        $user = $em->getRepository(Users::class)->findOneBy(['id'=>$uid]);

        $quizes = $quizzRepository->getHelperQuizListQueryBuilder($user,$q)->getQuery()->getResult();

        $json = $serializer->serialize($quizes, 'json',['groups'=>'quizz:list']);

        return new JsonResponse([
           'quizes' => $json
        ]);
    }

    /**
     * @Route("/create/{uid}/{title}/{subject}", name="quiz_create")
     */
    public function newJSON($uid, $title, $subject,Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $user = $em->getRepository(Users::class)->findOneBy(['id'=>$uid]);
        $quiz = new Quizz();

        $quiz->setHelper($user);
        $quiz->setTitle($title);
        $quiz->setSubject($subject);

        $em->persist($quiz);
        $em->flush();

        $json = $serializer->serialize($quiz, 'json',['groups'=>'quizz']);

        return new JsonResponse([
           'quiz' => $json
        ]);
    }

    /**
     * @Route("/{id}/delete", name="quiz_delete")
     */
    public function deleteJSON($id,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Quizz::class);

        $quiz= $repo->findOneBy([
            'id' => $id
        ]);

        $em->remove($quiz);
        $em->flush();

        return new JsonResponse([
            'oof' => "ahou khdém"
        ]);
    }

    /**
     * @Route("/{id}/edit/{title}/{subject}", name="quiz_edit", methods={"POST","GET"})
     */
    public function editJSON($id,$title,$subject, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Quizz::class);

        $quiz = $repo->findOneBy(['id'=>$id]);

        $quiz->setTitle($title);
        $quiz->setSubject($subject);

        $em->persist($quiz);
        $em->flush();

        $json = $serializer->serialize($quiz,'json',['groups'=>'quizz']);

        return new JsonResponse([
            'quiz'=>$json
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
