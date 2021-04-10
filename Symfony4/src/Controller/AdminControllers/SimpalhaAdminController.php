<?php

namespace App\Controller\AdminControllers;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class SimpalhaAdminController extends AbstractController
{
    /**
     * @Route("/admin/controllers/simpalha/admin", name="admin_controllers_simpalha_admin")
     */
    public function index(): Response
    {
        return $this->render('admin_controllers/simpalha_admin/index.html.twig', [
            'controller_name' => 'SimpalhaAdminController',
        ]);
    }
}
