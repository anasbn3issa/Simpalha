<?php

namespace App\Controller\UserControllers;

use App\Entity\Answer;
use App\Entity\Question;
use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuestionEditFormType;
use App\Form\QuestionFormType;
use Doctrine\ORM\EntityManagerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/quiz/{id}/questions")
 */
class QuestionController extends AbstractController
{
    /**
     * @Route("/", name="question_list")
     * @IsGranted("ROLE_USER")
     */
    public function list($id,EntityManagerInterface $em): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quiz = $repository->findOneBy(
            ['id' => $id]
        );

        $questions = $quiz->getQuestions();

        return $this->render('user_controllers/question/list.html.twig', [
            'questions' => $questions,
            'quizzId' => $id
        ]);
    }

    /**
     * @Route("/create", name="question_create")
     * @IsGranted("ROLE_USER")
     */
    public function new($id,Request $request, EntityManagerInterface $em): Response
    {

        $question = new Question();

        $quizz = $em->getRepository(Quizz::class)->findOneBy(['id'=>$id]);

        $form = $this->createForm(QuestionFormType::class, $question);

        $form->handleRequest($request);

        if($form->isSubmitted() && $form->isValid()) {

            /** @var Question $question */

            $answer = new Answer();
            $answer->setSuggestion("");

            $question = $form->getData();
            $question->setQuizz($quizz);
            $question->setRightAnswer($answer);

            $em->persist($question);
            $em->flush();

            $this->addFlash('success','You successfully added a new question to your Quiz');

            return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$question->getId()]);
        }

        return $this->render('user_controllers/question/new.html.twig', [
            'questionForm' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{qId}/edit", name="question_edit")
     * @IsGranted("ROLE_USER")
     */
    public function edit($id,$qId, EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Question::class);

        $question = $repo->findOneBy(['id'=>$qId]);

        $answersRepo = $em->getRepository(Answer::class);


        $form = $this->createForm(QuestionEditFormType::class, $question, [
            'question'=>$question,
        ]);

        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()) {

            $question = $form->getData();

            $em->persist($question);
            $em->flush();

            $this->addFlash('success','Question '.$question->getQuestion().' updated. Never say no, to making it better!');

            return $this->redirectToRoute('answer_list',['id'=>$id,'qId'=>$question->getId()]);
        }

        if($answersRepo->countAnswersToQuestion($qId) == 0)
        {
            return $this->render('user_controllers/question/update.html.twig', [
                'questionForm' => $form->createView(),
                'answerExists' => 0,
                'quizzId' => $id,
                'questionId' => $qId
            ]);
        }
        else{
            return $this->render('user_controllers/question/update.html.twig', [
                'questionForm' => $form->createView(),
                'answerExists' => 1,
                'quizzId' => $id,
                'questionId' => $qId
            ]);

        }
    }

    /**
     * @Route("/{qId}/delete", name="question_delete")
     * @IsGranted("ROLE_USER")
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

        return $this->redirectToRoute('question_list',['id'=>$id]);
    }


}
