<?php

namespace App\Controller\UserControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Form\AnswerFormType;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;



/**
 * @Route("/quiz/{id}/questions/{qId}/answers")
 */
class AnswerController extends AbstractController
{
    /**
     * @Route("/", name="answer_list")
     * @IsGranted("ROLE_USER")
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
     * @Route("/create", name="answer_create")
     * @IsGranted("ROLE_USER")
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
     * @Route("/edit", name="answer_edit")
     * @IsGranted("ROLE_USER")
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
     * @Route("/{aId}/delete", name="answer_delete")
     * @IsGranted("ROLE_USER")
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

    /**
     * @Route("/{aId}/right", name="answer_right")
     * @IsGranted("ROLE_USER")
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

        $title = $answer->getSuggestion();

        $question->setRightAnswer($answer);

        $em->persist($question);
        $em->flush();

        $this->addFlash('success','Answer '.$title.' was successfully labeled as right answer!');

        return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$qId]);
    }


}
