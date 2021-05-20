<?php

namespace App\Controller\Mobile\ressources;

use App\Entity\Ressources;
use App\Entity\Module;
use App\Form\Ressources1Type;
use App\Repository\ModulesRepository;
use App\Repository\RessourcesRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;


/**
 * @Route("/mobile/ressources")
 */
class RessourcesController extends AbstractController
{

    //affichage
    /**
     * @Route("/", name="mobile_ressources_index", methods={"GET"})
     */
    public function index(RessourcesRepository $ressourcesRepository, ModulesRepository $modulesRepository,SerializerInterface $serializerInterface,NormalizerInterface $normalizer): Response
    {
        /*video : serializer
        $ressources=$ressourcesRepository->findAll();
        $json=$serializerInterface->serialize($ressources,'json',['groups'=>'$ressource']);
        dump($ressources);
        die;
        */

        //workshop: normalizer
        $ressources=$ressourcesRepository->findAll();
        $jsonContent= $normalizer->normalize($ressources, 'json',['groups'=>'ressource']);
        return new Response(json_encode($jsonContent));


    }

    /**
     * @Route("/{idr}", name="mobile_ressources_show", methods={"GET","POST"})
     */
    public function show(Request $request, $idr,NormalizerInterface $normalizer): Response
    {

        $em=$this->getDoctrine()->getManager();
        $ressource= $em->getRepository(Ressources::class)->find($idr);
        $jsonContent= $normalizer->normalize($ressource, 'json',['groups'=>'ressource']);

        return new Response(json_encode($jsonContent));
    }


    //ajout
    /**
     * @Route("/new", name="mobile_ressources_new", methods={"GET","POST"})
     */
    public function new(Request $request, NormalizerInterface $normalizer): Response
    {
        /*video:serializer
        $content=$request->getContent();
        $data=$serializer->deserialize($content,Ressources::class,'json');
        $em->persist($data);
        $em->flush();
        return new Response('resource added ');
        */

        $em=$this->getDoctrine()->getManager();
        $ressource = new Ressources();
        $ressource->setPath($request->get('path'));
        $ressource->setTitle($request->get('title'));
        $ressource->setDescription($request->get('description'));
        $ressource->setModule($request->get('module'));

        $em->persist($ressource);
        $em->flush();
        $jsonContent = $normalizer->normalize($ressource, 'json',['groups'=>'ressource']);
        return new Response(json_encode($jsonContent));


    }



    /**
     * @Route("/{idr}/edit", name="mobile_ressources_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, NormalizerInterface $normalizer,$idr): Response
    {
       $em= $this->getDoctrine()->getManager();
       $ressource=$em->getRepository(Ressources::class)->find($idr);
        $ressource->setPath($request->get('path'));
        $ressource->setTitle($request->get('title'));
        $ressource->setDescription($request->get('description'));
        $ressource->setModule($request->get('module'));
        $em->flush();
        $jsonContent = $normalizer->normalize($ressource, 'json',['groups'=>'ressource']);
        return new Response("Resource updated".json_encode($jsonContent));


    }

    /**
     * @Route("/{idr}/delete", name="mobile_ressources_delete")
     */
    public function delete(Request $request, NormalizerInterface $normalizer,$idr): Response
    {
    $em=$this->getDoctrine()->getManager();
    $ressource= $em->getRepository(Ressources::class)->find($idr);
    $em->remove($ressource);
    $em->flush();
        $jsonContent = $normalizer->normalize($ressource, 'json',['groups'=>'ressource']);
        return new Response("Resource deleted".json_encode($jsonContent));

    }

}

