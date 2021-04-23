<?php

namespace App\Controller\AdminControllers;

use App\Entity\Post;
use App\Form\Post1Type;
use App\Repository\CommentRepository;
use App\Repository\PostRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/adminUser")
 */
class AdminController extends AbstractController
{
    /**
     * @Route("/", name="admin_homepage", methods={"GET"})
     */
    public function index(): Response
    {
        return $this->render('indexAdmin.html.twig');
    }


}
