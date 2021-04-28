<?php

namespace App\Controller\UserControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;


/**
 * @Route("/user/simpalha")
 */
class SimpalhaUserController extends AbstractController
{
    /**
     * @Route("/", name="user/simpalha")
     */
    public function index(): Response
    {
        return $this->render('user_controllers/simpalha_user/index.html.twig', [
            'controller_name' => 'SimpalhaUserController',
        ]);
    }
}
