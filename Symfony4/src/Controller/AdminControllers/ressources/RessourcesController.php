<?php

namespace App\Controller\AdminControllers\ressources;

use App\Entity\Ressources;
use App\Entity\Module;
use App\Form\Ressources1Type;
use App\Repository\ModulesRepository;
use App\Repository\RessourcesRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;



/**
 * @Route("/ressources")
 */
class RessourcesController extends AbstractController
{
    /**
     * @Route("/", name="ressources_index", methods={"GET"})
     */
    public function index(RessourcesRepository $ressourcesRepository, ModulesRepository $modulesRepository): Response
    {
        return $this->render('admin_controllers/ressources/index.html.twig', [
            'ressources' => $ressourcesRepository->findAll(),
            'modules' => $modulesRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="ressources_new", methods={"GET","POST"})
     */
    public function new(Request $request, RessourcesRepository $ressourcesRepository): Response
    {
        $ressource = new Ressources();
        $form = $this->createForm(Ressources1Type::class, $ressource);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $ressource->getPath();
            $fileName = md5(uniqid()) . '.' . $file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $ressource->setPath('/uploads/' . $fileName);
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($ressource);
            $entityManager->flush();
/*
            // SMS API
            $basic  = new \Vonage\Client\Credentials\Basic("34e34089", "BgEEVv60NIjzoVqH");
            $client = new \Vonage\Client($basic);
            $response = $client->sms()->send(
              new \Vonage\SMS\Message\SMS("21695970796", "Simpalha WEBSITE", "New ressource added, check simpal ha for more ! ")
                );
            $message = $response->current();

            if ($message->getStatus() == 0) {
               echo "The message was sent successfully\n";
            } else {
                echo "The message failed with status: " . $message->getStatus() . "\n";
            }
          //  END OF SMS API
*/

            return $this->redirectToRoute('ressources_index');
        }

        return $this->render('admin_controllers/ressources/new.html.twig', [
            'ressource' => $ressource,
            'form' => $form->createView(),

            'ressources' => $ressourcesRepository->findAll(),

        ]);
    }

    /**
     * @Route("/{idr}", name="ressources_show", methods={"GET"})
     */
    public function show(Ressources $ressource): Response
    {
        return $this->render('admin_controllers/ressources/show.html.twig', [
            'ressource' => $ressource,
        ]);
    }

    /**
     * @Route("/{idr}/edit", name="ressources_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Ressources $ressource): Response
    {
        $form = $this->createForm(Ressources1Type::class, $ressource);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $file = $ressource->getPath();
            $fileName = md5(uniqid()) . '.' . $file->guessExtension();
            $file->move($this->getParameter('upload_directory'), $fileName);
            $ressource->setPath("/uploads/" . $fileName);


            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('ressources_index');
        }

        return $this->render('admin_controllers/ressources/edit.html.twig', [
            'ressource' => $ressource,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idr}", name="ressources_delete", methods={"POST"})
     */
    public function delete(Request $request, Ressources $ressource): Response
    {
        if ($this->isCsrfTokenValid('delete' . $ressource->getIdr(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($ressource);
            $entityManager->flush();
        }

        return $this->redirectToRoute('ressources_index');
    }
    ///////////////////////Recherche av /////////////
    /**
     *@Route("searchajax", name="ajaxsearch")
     */
    public function searchAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $requestString = $request->get('q');
        $ressources = $em->getRepository(Ressources::class)->findEntitiesByString($requestString);
        if(!$ressources)
        {
            $result['ressources']['error']="No resource having this module :( ";

        }else{
            $result['ressources']=$this->getRealEntities($ressources);
        }
        return new Response(json_encode($result));

    }
    public function getRealEntities($ressources){
        foreach ($ressources as $ressources){
            $realEntities[$ressources->getIdr()] = [$ressources->getModule()];
        }
        return $realEntities;
    }

    /// ////////////////end recherche av//////////////
}

