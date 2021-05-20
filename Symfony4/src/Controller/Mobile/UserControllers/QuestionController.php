<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\AbstractNormalizer;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/quiz/{id}/questions",name="mobile_")
 */
class QuestionController extends AbstractController
{
    /**
     * @Route("/", name="question_list")
     */
    public function listJSON($id,EntityManagerInterface $em, SerializerInterface $serializer): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $json = $serializer->serialize($quiz->getQuestions(), 'json',['groups'=>'question:list']);

        return new JsonResponse([
            'questions' => $json,
            'quizzId' => $id
        ]);
    }

    /**
     * @Route("/create/{questionText}", name="question_create")
     */
    public function newJSON($id,$questionText,Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $quizz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);
        $question = new Question();
        $answer = new Answer();
        $answer->setSuggestion("");

        $question->setQuestion($questionText);
        $question->setQuizz($quizz);
        $question->setRightAnswer($answer);

        $em->persist($question);
        $em->flush();

        $json = $serializer->serialize($quizz, 'json',['groups'=>'quizz']);

        return new JsonResponse([
            'quiz' => $json
        ]);
    }

    /**
     * @Route("/{qId}/edit/{questionText}", name="question_edit")
     */
    public function editJSON($id,$qId,$questionText, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Question::class);
        $question = $repo->findOneBy(['id'=>$qId]);
        $answersRepo = $em->getRepository(Answer::class);

        $question->setQuestion($questionText);

        $em->persist($question);
        $em->flush();

        $json = $serializer->serialize($question, 'json',['groups'=>'question']);

        if($answersRepo->countAnswersToQuestion($qId) == 0)
        {
            return new JsonResponse([
                'question' => $json,
                'answerExists' => 0,
                'quizzId' => $id,
                'questionId' => $qId
            ]);
        }
        else{
            return new JsonResponse([
                'question' => $json,
                'answerExists' => 1,
                'quizzId' => $id,
                'questionId' => $qId
            ]);
        }
    }

    /**
     * @Route("/{qId}/delete", name="question_delete")
     */
    public function deleteJSON($id,$qId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Question::class);

        $question= $repo->findOneBy([
            'id' => $qId
        ]);

        $em->remove($question);
        $em->flush();

        return new JsonResponse([
            'deleted' => true
        ]);
    }


}
