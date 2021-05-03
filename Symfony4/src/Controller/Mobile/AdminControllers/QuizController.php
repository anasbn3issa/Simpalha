<?php

namespace App\Controller\Mobile\AdminControllers;

use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuizFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/admin/quiz",name="mobile_")
 */
class QuizController extends AbstractController
{
    /**
     * @Route("/list", name="admin_quiz_list")
     */
    public function list(EntityManagerInterface $em, SerializerInterface $serializer): Response
    {
        $repository = $em->getRepository(Quizz::class);

        $quizes = $repository->findAll();

        $json = $serializer->serialize($quizes, 'json',['groups'=>'quizz']);

        return $this->render('admin_controllers/quiz/list.html.twig', [
            'quizzs' => $json,
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
