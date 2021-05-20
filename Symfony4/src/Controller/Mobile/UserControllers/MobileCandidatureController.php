<?php


namespace App\Controller\Mobile\UserControllers;


use App\Entity\Candidatures;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use App\Service\FileUploader;
/**
     * @Route("/mobile")
 */
class MobileCandidatureController extends AbstractController
{
    /**
     * @Route("/candidature/new", name="newCandidature")
     * @param Request $request
     * @return Response
     * @param FileUploader $fileUploader
     */
    public function addCandidature(FileUploader $fileUploader,Request $request): Response
    {


       $em = $this->getDoctrine()->getManager();
        $loggedUser = $this->getUser();
        //  $id = $em->getRepository(f)

     //   $candidature = $em->getRepository(Candidatures::class)->findOneByUserId($loggedUser);
        $candidature = new Candidatures();
        $candidature->setDescription($request->get('description'));
        $candidature->setSpecialty($request->get('speciality'));
        $candidature->setDatec($request->get('datec',new \DateTime));
 	 $parametersAsArray = json_decode($request->getContent(), true);
     	$newFilename = $fileUploader->upload($parametersAsArray['file']);
        $candidature->setFichier($newFilename);
        $em->persist($candidature);
        $em->flush();
        return new JsonResponse('OK');
    }
}