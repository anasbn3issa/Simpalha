<?php

namespace App\Controller\UserControllers;

use App\Entity\Reclamation;
use App\Form\ReclamationAdminType;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @Route("/user/controllers/reclamation/user")
 */
class ReclamationUserController extends AbstractController
{

    /**
     * @Route("/", name="user_controllers_reclamation_user_index", methods={"GET"})
     */
    public function index(ReclamationRepository $reclamationRepository): Response
    {
        return $this->render('user_controllers/reclamation_user/index.html.twig', [
            'reclamations' => $reclamationRepository->findAll(),
        ]);
    }



    /**
     * @Route("/newRecUser", name="user_controllers_reclamation_user_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $image=$request->files->get('reclamation')['FileSelected'];
            $uploads_directory=$this->getParameter('kernel.root_dir'). '/../public/img';
            $filename=md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploads_directory,
                $filename
            );

            $reclamation = $form->getData();
            $reclamation->setFileselected($filename);



            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($reclamation);
            $entityManager->flush();

            return $this->redirectToRoute('user_controllers_reclamation_user_index');
        }
        return $this->render('user_controllers/reclamation_user/new.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);

    }

    /**
     * @Route("/uploadrecord", name="uploadrecord", methods={"GET","POST" })
     */
    public function uploadrecord (Request $request): Response
    {
        /*$image=$request->files->get('audio_data');
        $uploads_directory=$this->getParameter('kernel.root_dir'). '/../public/img';
        $filename=md5(uniqid()) . '.' . $image->guessExtension();
        $image->move(
            $uploads_directory,
            $filename
        );*/
        dd($request);
        return new Response ("record");

    }


    /**
     * @Route("/{id}", name="user_controllers_reclamation_user_show", methods={"GET","POST" })
     */
    public function show (Reclamation $reclamation ,Request $request): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid())
        {
            $this->getDoctrine()->getManager()->flush();
            return $this->redirectToRoute(admin_controllers_reclamation_admin_index);
        }
        return $this->render('user_controllers/reclamation_user/show.html.twig', [
            'reclamation' => $reclamation,
             'form'=>$form->createView(),  ]);

    }


    /**
     * @Route("/{id}/editRecUser", name="user_controllers_reclamation_user_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Reclamation $reclamation): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $image=$request->files->get('reclamation')['FileSelected'];
            $uploads_directory=$this->getParameter('kernel.root_dir'). '/../public/img';
            $filename=md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploads_directory,
                $filename
            );

            $reclamation->setFileselected($filename);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_controllers_reclamation_user_index');
        }

        return $this->render('user_controllers/reclamation_user/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);

    }
    /**
     * @Route("/Validate/{id}", name="user_controllers_reclamation_user_validate", methods={"GET","POST"})
     */
    public function validate($id, ReclamationRepository $repository)
    { $reclamation = $repository->find($id);
        $reclamation->setStatus(1);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();
        return $this->redirectToRoute("user_controllers_reclamation_user_index");
        return $this->render("reclamation_user/edit.html.twig");
    }


    /**
     * @Route("/Supp/{id}", name="user_controllers_reclamation_user_delete",)
     */
    public function delete($id,ReclamationRepository $repository)
    { $reclamation=$repository->find($id);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($reclamation);
        $entityManager->flush();

        return $this->redirectToRoute('user_controllers_reclamation_user_index');

    }
}
