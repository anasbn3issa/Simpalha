<?php

namespace App\Controller\Mobile\AdminControllers;

use App\Entity\Question;
use App\Entity\Quizz;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/admin/quiz/{id}/questions", name="mobile_")
 */
class QuestionController extends AbstractController
{
    /**
     * @Route("/", name="admin_question_list")
     */
    public function list($id,SerializerInterface $serializer,EntityManagerInterface $em): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $questions = $quiz->getQuestions();

        $json = $serializer->serialize($questions, 'json',['groups'=>'question']);

        return new JsonResponse([
            'questions' => $json,
            'quizzId' => $id
        ]);
    }

    /**
     * @Route("/{qId}/delete", name="admin_question_delete")
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
