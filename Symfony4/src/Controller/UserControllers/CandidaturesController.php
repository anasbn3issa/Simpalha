<?php

namespace App\Controller\UserControllers;

use App\Entity\Candidatures;


use App\Form\CandidaturesType;
use App\Service\FileUploader;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Constraints\Date;

/**
     * @Route("/candidatures")
 */
class CandidaturesController extends AbstractController
{
    /**
     * @Route("/", name="candidatures_index", methods={"GET"})
     * @IsGranted("ROLE_ADMIN")
     */
    public function index(): Response
    {
        $candidatures = $this->getDoctrine()
            ->getRepository(Candidatures::class)->findAll();

        return $this->render('candidatures/index.html.twig', [
            'candidatures' => $candidatures,
        ]);
    }

    /**
     * @Route("/new", name="candidatures_new", methods={"GET","POST"})
     * @param FileUploader $fileUploader
     * @param Request $request
     * @return Response
     */
    public function new(FileUploader $fileUploader, Request $request): Response
    {
        $candidature = new Candidatures();
        $form = $this->createForm(CandidaturesType::class, $candidature);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();

            /** @var UploadedFile $certificationFile */
            $certificationFile = $form->get('fichier')->getData();
            if ($certificationFile) {
                $newFilename = $fileUploader->upload($certificationFile);
                $candidature->setFichier($newFilename);
            }
            $candidature->setDatec(new \DateTime);
            $candidature->setStatus(0);
            $candidature->setCreatedBy($this->getUser());
            $entityManager->persist($candidature);
            $entityManager->flush();
            $this->addFlash('success', 'Application added successfully');
            return $this->redirectToRoute('user_controllers_post_index');
        }

        return $this->render('candidatures/new.html.twig', [
            'candidature' => $candidature,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idc}", name="candidatures_show", methods={"GET"})
     */
    public function show(Candidatures $candidature): Response
    {
        return $this->render('candidatures/show.html.twig', [
            'candidature' => $candidature,
        ]);
    }

    /**
     * @Route("/{idc}/edit", name="candidatures_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Candidatures $candidature): Response
    {
        $form = $this->createForm(CandidaturesType::class, $candidature);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('candidatures_index');
        }

        return $this->render('candidatures/edit.html.twig', [
            'candidature' => $candidature,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{idc}", name="candidatures_delete", methods={"POST"})
     * @param Request $request
     * @param Candidatures $candidature
     * @return Response
     */
    public function delete(Request $request, Candidatures $candidature): Response
    {
        if ($this->isCsrfTokenValid('delete' . $candidature->getIdc(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($candidature);
            $entityManager->flush();
        }

        return $this->redirectToRoute('candidatures_index');
    }

    /**
     * @Route("/users/changeStatus/{id}/{type}", name="candidatures_isActive")
     */
    public function CandidatureChangeIsActive(int $id, int $type)
    {
        if (!$this->isGranted('ROLE_ADMIN')) {
            return $this->redirect($this->generateUrl('admin_homepage'));
        }

        $em = $this->getDoctrine()->getManager();
        $candidature = $this->getDoctrine()->getRepository(Candidatures::class)->find($id);
        $type ? $candidature->setStatus(1) : $candidature->setStatus(2);
        $candidature->getCreatedBy()->setRoles(array('ROLE_HELPER'));
        $helper = $candidature->getCreatedBy();
        $helper->setSpecialite($candidature->getSpecialty());
        $em->persist($candidature);
        $em->persist($helper);
        $em->flush();
        $this->addFlash('success', 'Application Status changed successfully');
        return $this->redirect($this->generateUrl('candidatures_index'));
    }

    /**
     * @Route("searchajax", name="ajaxsearch")
     */
    public function searchAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $requestString = $request->get('q');
        $candidatures = $em->getRepository(Candidatures::class)->findEntitiesByString($requestString);
        if (!$candidatures) {
            $result['candidatures']['error'] = "No application found :( ";

        } else {
            $result['candidatures'] = $this->getRealEntities($candidatures);
        }
        return new Response(json_encode($result));

    }

    public function getRealEntities($candidatures)
    {
        foreach ($candidatures as $candidatures){

            $realEntities[$candidatures->getIdc()] = [$candidatures->getIdc(),
                $candidatures->getCreatedBy()->getPseudo(),
                $candidatures->getFichier(),
                $candidatures->getDescription(),
                $candidatures->getStatus(),
                $candidatures->getDatec()->format('Y-m-d H:i:s')
            ];
        }
        return $realEntities;
    }


}
