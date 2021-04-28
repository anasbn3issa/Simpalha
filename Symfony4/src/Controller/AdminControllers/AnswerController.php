<?php

namespace App\Controller\AdminControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Form\AnswerFormType;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/admin/quiz")
 */
class AnswerController extends AbstractController
{
    /**
     * @Route("/{id}/questions/{qId}/answers", name="admin_answer_list")
     */
    public function list($id,$qId,EntityManagerInterface $em): Response
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

        return $this->render('admin_controllers/answer/list.html.twig', [
            'answers' => $answers,
            'question' => $question,
            'quizzId' => $id,
            'questionId' => $qId,
        ]);
    }

    /**
     * @Route("/{id}/question/{qId}/answer/{aId}/delete", name="admin_answer_delete")
     */
    public function delete($id,$qId,$aId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Answer::class);

        $answer= $repo->findOneBy([
            'id' => $aId
        ]);

        $title = $answer->getSuggestion();

        $em->remove($answer);
        $em->flush();

        $this->addFlash('failure','Answer '.$title.' was removed from your possible Answers.');

        return $this->redirectToRoute('admin_answer_list',['id'=>$id,'qId'=>$qId]);
    }
}
