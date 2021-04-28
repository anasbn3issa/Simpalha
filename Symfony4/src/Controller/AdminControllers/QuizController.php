<?php

namespace App\Controller\AdminControllers;

use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuizFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/admin/quiz")
 */
class QuizController extends AbstractController
{
    /**
     * @Route("/", name="admin_quiz")
     */
    public function index(): Response
    {
        return $this->render('admin_controllers/quiz/index.html.twig');
    }

    /**
     * @Route("/list", name="admin_quiz_list")
     */
    public function list(EntityManagerInterface $em): Response
    {
        $repository = $em->getRepository(Users::class);

        $quizes = $repository->findAll();

        return $this->render('admin_controllers/quiz/list.html.twig', [
            'quizzs' => $quizes,
        ]);
    }

    /**
     * @Route("/{id}/delete", name="admin_quiz_delete")
     */
    public function delete($id,EntityManagerInterface $em,Request $request)
    {
        $repo = $em->getRepository(Quizz::class);

        $quiz= $repo->findOneBy([
            'id' => $id
        ]);

        $title = $quiz->getTitle();

        $em->remove($quiz);
        $em->flush();

        $this->addFlash('failure','Quiz '.$title.' was deleted from your list..');

        return $this->redirectToRoute('admin_quiz_list');
    }




}
