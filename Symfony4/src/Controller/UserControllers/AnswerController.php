<?php

namespace App\Controller\UserControllers;

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

class AnswerController extends AbstractController
{
    /**
     * @Route("/quiz/{id}/questions/{qId}/answers", name="answer_list")
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

        return $this->render('user_controllers/answer/list.html.twig', [
            'answers' => $answers,
            'question' => $question,
            'quizzId' => $id,
            'questionId' => $qId,
        ]);
    }

    /**
     * @Route("/quiz/{id}/question/{qId}/answer/create", name="answer_create")
     */
    public function new($id,$qId,Request $request, EntityManagerInterface $em): Response
    {

        $answer = new Answer();

        $question = $em->getRepository(Question::class)->findOneBy(['id'=>$qId]);

        $form = $this->createForm(AnswerFormType::class, $answer);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {

            /** @var Answer $answer */

            $answer = $form->getData();

            $answer->setQuestion($question);

            $em->persist($answer);
            $em->flush();

            $this->addFlash('success','You successfully added a new possible answer to your Question');

            return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$qId]);
        }

        return $this->render('user_controllers/answer/new.html.twig', [
            'answerForm' => $form->createView(),
        ]);
    }

    /**
     * @Route("/quiz/{id}/question/{qId}/answer/{aId}/edit", name="answer_edit")
     */
    public function edit($id,$qId,$aId, EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Answer::class);

        $answer = $repo->findOneBy(['id'=>$aId]);

        $form = $this->createForm(AnswerFormType::class, $answer);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {

            $answer = $form->getData();

            $em->persist($answer);
            $em->flush();

            $this->addFlash('success','Answer '.$answer->getSuggestion().' updated!');

            return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$qId]);
        }

        return $this->render('user_controllers/answer/update.html.twig', [
            'answerForm' => $form->createView()
        ]);
    }

    /**
     * @Route("/quizz/{id}/question/{qId}/answer/{aId}/delete", name="answer_delete")
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

        return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$qId]);
    }
}
