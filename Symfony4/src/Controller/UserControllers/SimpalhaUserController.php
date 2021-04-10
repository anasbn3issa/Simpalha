<?php

namespace App\Controller\UserControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SimpalhaUserController extends AbstractController
{
    /**
     * @Route("/user/controllers/simpalha/user", name="user_controllers_simpalha_user")
     */
    public function index(): Response
    {
        return $this->render('user_controllers/simpalha_user/index.html.twig', [
            'controller_name' => 'SimpalhaUserController',
        ]);
    }
}
