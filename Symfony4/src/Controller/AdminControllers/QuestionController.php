<?php

namespace App\Controller\AdminControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class QuestionController extends AbstractController
{
    /**
     * @Route("/admin/quiz/{id}/questions", name="admin_question_list")
     */
    public function list($id,EntityManagerInterface $em): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $questions = $quiz->getQuestions();

        return $this->render('admin_controllers/question/list.html.twig', [
            'questions' => $questions,
            'quizzId' => $id
        ]);
    }

    /**
     * @Route("/admin/quizz/{id}/question/{qId}/delete", name="admin_question_delete")
     */
    public function delete($id,$qId,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Question::class);

        $question= $repo->findOneBy([
            'id' => $qId
        ]);

        $title = $question->getQuestion();

        $em->remove($question);
        $em->flush();

        $this->addFlash('failure','Question '.$title.' was removed from your Quiz. Knowledge can now go forward!');

        return $this->redirectToRoute('admin_question_list',['id'=>$id]);
    }


}
