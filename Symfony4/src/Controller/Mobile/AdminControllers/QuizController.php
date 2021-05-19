<?php

namespace App\Controller\Mobile\AdminControllers;

use App\Entity\Quizz;
use App\Entity\Users;
use App\Form\QuizFormType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Validator\Constraints\Json;


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

        return new JsonResponse([
           'quizList' => $json
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

        $em->remove($quiz);
        $em->flush();
    }




}
