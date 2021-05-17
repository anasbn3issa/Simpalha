<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Form\AnswerFormType;
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
 * @Route("/mobile/quiz/{id}/questions/{qId}/answers",name="mobile_")
 */
class AnswerController extends AbstractController
{
    /**
     * @Route("/", name="answer_list")
     */
    public function list($id,$qId,EntityManagerInterface $em, SerializerInterface $serializer): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $repository = $em->getRepository(Question::class);

        $question = $repository->findOneBy(
            ['id' => $qId]
        );

        $answers = $question->getAnswers();

        $json = $serializer->serialize($answers, 'json',['groups'=>'answer:list']);

        return new JsonResponse([
            'answers' => $json,
            'quizzId' => $id,
            'questionId' => $qId,
        ]);
    }

    /**
     * @Route("/create/{suggestion}", name="answer_create")
     */
    public function newJSON($id,$qId,$suggestion,Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $question = $em->getRepository(Question::class)->findOneBy(['id'=>$qId]);
        $answer = new Answer();

        $answer->setSuggestion($suggestion);
        $answer->setQuestion($question);

        $em->persist($answer);
        $em->flush();

        $json = $serializer->serialize($question, 'json',['groups'=>'question']);

        return new JsonResponse([
           'question' => $json
        ]);
    }

    /**
     * @Route("/{aId}/edit/{suggestion}", name="answer_edit", methods={"POST","GET"})
     */
    public function editJSON($id,$qId,$aId,$suggestion, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Answer::class);

        $answer = $repo->findOneBy(['id'=>$aId]);

        $answer->setSuggestion($suggestion);

        $em->persist($answer);
        $em->flush();

        $json = $serializer->serialize($answer, 'json',['groups'=>'answer']);

        return new JsonResponse([
           'answer' => $json
        ]);
    }

    /**
     * @Route("/{aId}/delete", name="answer_delete")
     */
    public function deleteJSON($id,$qId,$aId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Answer::class);

        $answer= $repo->findOneBy([
            'id' => $aId
        ]);

        $em->remove($answer);
        $em->flush();

        return new JsonResponse([
            'deleted' => true
        ]);
    }

    /**
     * @Route("/{aId}/right", name="answer_right")
     */
    public function rightAnswerJSON($id,$qId,$aId,EntityManagerInterface $em,Request $request)
    {
        $questionRepo = $em->getRepository(Question::class);
        $repo = $em->getRepository(Answer::class);

        $question = $questionRepo->findOneBy([
            'id' => $qId
        ]);

        $answer= $repo->findOneBy([
            'id' => $aId
        ]);

        $question->setRightAnswer($answer);

        $em->persist($question);
        $em->flush();

        return new JsonResponse([
            'right' => true
        ]);
    }


}
