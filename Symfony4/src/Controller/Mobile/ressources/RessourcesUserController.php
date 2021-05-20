<?php

namespace App\Controller\Mobile\ressources;

use App\Entity\Ressources;
use App\Repository\ModulesRepository;
use App\Repository\RessourcesRepository;
use Knp\Snappy\Pdf;
use Symfony\Bridge\Twig\Extension\HttpFoundationExtension;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\FrameworkBundle\Templating\Helper\AssetsHelper;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("/mobile/RUser")
 */
class RessourcesUserController extends AbstractController
{/**
 * @Route("/", name="mobile_ressourcesUser_index", methods={"GET"})
 */
    public function index(RessourcesRepository $ressourcesRepository, ModulesRepository $modulesRepository,SerializerInterface $serializer,NormalizerInterface $normalizer): Response
    {
        /*video : serializer
        $ressources=$ressourcesRepository->findAll();
        $json=$serializerInterface->serialize($ressources,'json',['groups'=>'$ressource']);
        dump($ressources);
        die;
        */

        //workshop: normalizer
        $ressources=$ressourcesRepository->findAll();
      ///  $jsonContent= $normalizer->normalize($ressources, 'json',['groups'=>'ressource']);
           //return new Response(json_encode($jsonContent));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($ressources);
        return new JsonResponse($formatted);



    }

    /**
     * @Route("/tri", name="mobile_ressourcesUser_tri", methods={"GET"})
     */
    public function tri(RessourcesRepository $ressourcesRepository, ModulesRepository $modulesRepository,SerializerInterface $serializer,NormalizerInterface $normalizer): Response
    {
        /*video : serializer
        $ressources=$ressourcesRepository->findAll();
        $json=$serializerInterface->serialize($ressources,'json',['groups'=>'$ressource']);
        dump($ressources);
        die;
        */

        //workshop: normalizer
        $ressources=$ressourcesRepository->OrderBy();
        ///  $jsonContent= $normalizer->normalize($ressources, 'json',['groups'=>'ressource']);
        //return new Response(json_encode($jsonContent));

        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($ressources);
        return new JsonResponse($formatted);



    }



    /**
     * @Route("/{idr}", name="mobile_ressourcesUser_show", methods={"GET"})
     */
    public function show(Request $request, $idr,NormalizerInterface $normalizer): Response
    {

        $em=$this->getDoctrine()->getManager();
        $ressource= $em->getRepository(Ressources::class)->find($idr);
        $jsonContent= $normalizer->normalize($ressource, 'json',['groups'=>'ressource']);

        return new Response(json_encode($jsonContent));
    }









}
