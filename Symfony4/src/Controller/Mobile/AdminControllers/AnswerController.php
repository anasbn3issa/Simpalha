<?php

namespace App\Controller\Mobile\AdminControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Form\AnswerFormType;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/admin/quiz/{id}/questions/{qId}/answers",name="mobile_")
 */
class AnswerController extends AbstractController
{
    /**
     * @Route("/", name="admin_answer_list")
     */
    public function list($id,$qId,EntityManagerInterface $em,SerializerInterface $serializer): Response
    {
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
     * @Route("/{aId}/delete", name="admin_answer_delete")
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
}
