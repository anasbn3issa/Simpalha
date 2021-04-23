<?php

namespace App\Controller\AdminControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SimpalhaAdminController extends AbstractController
{
    /**
     * @Route("/adminUser/simpalha", name="adminUser/simpalha")
     */
    public function index(): Response
    {
        return $this->render('admin_controllers/simpalha_admin/index.html.twig', [
            'controller_name' => 'SimpalhaAdminController',
        ]);
    }
}
