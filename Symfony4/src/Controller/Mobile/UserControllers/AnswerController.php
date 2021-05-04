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

        $json = $serializer->serialize($question, 'json',['groups'=>'question']);

        return new JsonResponse([
            'question' => $json,
            'quizzId' => $id,
            'questionId' => $qId,
        ]);
    }

    /**
     * @Route("/create", name="answer_create", methods={"POST","GET"})
     */
    public function new($id,$qId,Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $question = $em->getRepository(Question::class)->findOneBy(['id'=>$qId]);

        if($request->getMethod() == "POST") {

            $answer = $request->getContent();

            $data=$serializer->deserialize($answer,Answer::class,'json');

            $data->setQuestion($question);

            $em->persist($data);
            $em->flush();
        }

        $json = $serializer->serialize($question, 'json',['groups'=>'question']);

        return new JsonResponse([
           'question' => $json
        ]);
    }

    /**
     * @Route("/{aId}/edit", name="answer_edit", methods={"POST","GET"})
     */
    public function edit($id,$qId,$aId, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Answer::class);

        $answer = $repo->findOneBy(['id'=>$aId]);

        if($request->getMethod() == "POST") {

            $content = $request->getContent();

            $data=$serializer->deserialize($content,Answer::class,'json', [AbstractNormalizer::OBJECT_TO_POPULATE => $answer]);

            $em->persist($data);
            $em->flush();

        }

        $json = $serializer->serialize($answer, 'json',['groups'=>'answer']);

        return new JsonResponse([
           'answer' => $json
        ]);
    }

    /**
     * @Route("/{aId}/delete", name="answer_delete")
     */
    public function delete($id,$qId,$aId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Answer::class);

        $answer= $repo->findOneBy([
            'id' => $aId
        ]);

        $em->remove($answer);
        $em->flush();
    }

    /**
     * @Route("/{aId}/right", name="answer_right")
     */
    public function rightAnswer($id,$qId,$aId,EntityManagerInterface $em,Request $request)
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
    }


}
