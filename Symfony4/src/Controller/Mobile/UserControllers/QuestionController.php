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
    public function list($id,EntityManagerInterface $em, SerializerInterface $serializer): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $json = $serializer->serialize($quiz, 'json',['groups'=>'quizz']);

        return new JsonResponse([
            'quiz' => $json,
            'quizzId' => $id
        ]);
    }

    /**
     * @Route("/create", name="question_create", methods={"POST","GET"})
     */
    public function new($id,Request $request, EntityManagerInterface $em, SerializerInterface $serializer): Response
    {

        $quizz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);

        if($request->getMethod() == "POST") {

            $answer = new Answer();
            $answer->setSuggestion("");

            $content = $request->getContent();
            $data=$serializer->deserialize($content,Question::class,'json');
            $data->setQuizz($quizz);
            $data->setRightAnswer($answer);

            $em->persist($data);
            $em->flush();
        }

        $json = $serializer->serialize($quizz, 'json',['groups'=>'quizz']);

        return new JsonResponse([
            'quiz' => $json
        ]);
    }

    /**
     * @Route("/{qId}/edit", name="question_edit", methods={"POST","GET"})
     */
    public function edit($id,$qId, EntityManagerInterface $em,Request $request, SerializerInterface $serializer)
    {
        $repo = $em->getRepository(Question::class);

        $question = $repo->findOneBy(['id'=>$qId]);

        $answersRepo = $em->getRepository(Answer::class);

        if($request->getMethod() == "POST") {

            $content = $request->getContent();

            $data=$serializer->deserialize($content,Question::class,'json', [AbstractNormalizer::OBJECT_TO_POPULATE => $question]);

            $em->persist($data);
            $em->flush();
        }

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
    public function delete($id,$qId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Question::class);

        $question= $repo->findOneBy([
            'id' => $qId
        ]);

        $em->remove($question);
        $em->flush();
    }


}
