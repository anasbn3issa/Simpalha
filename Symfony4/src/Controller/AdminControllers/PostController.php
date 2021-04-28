<?php

namespace App\Controller\AdminControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\PostRepository;


class PostController extends AbstractController
{


    /**
     * @Route("/admin/posts", name="admin_controller_post_list")
     */
    public function list(PostRepository $postRepository )
    {
        $posts = $postRepository->findAll();

        return $this->render('admin_controllers/simpalha_admin/listPosts.html.twig',[
            'posts' => $posts
        ]);
    }



}
