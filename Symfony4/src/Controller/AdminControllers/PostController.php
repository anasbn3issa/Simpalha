<?php

namespace App\Controller\AdminControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\PostRepository;


class PostController extends AbstractController
{
    /**
     * @Route("/admin/post", name="admin_controllers_post")
     */
    public function index(PostRepository $postRepository): Response
    {
        return $this->render('user_controllers/post/index.html.twig', [
            'posts' => $postRepository->findAll(),
        ]);
    }
}
