<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Disponibilite;
use App\Entity\Users;
use App\Form\DisponibiliteType;
use App\Repository\DisponibiliteRepository;
use App\Repository\MeetRepository;
use App\Repository\UsersRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("mobile/disponibilite")
 */
class MobileDisponibiliteController extends AbstractController
{
    /**
     * @Route("/", name="helper_disponibilite_index", methods={"GET"})
     */
    public function index(DisponibiliteRepository $disponibiliteRepository, UsersRepository $userRepository): Response
    {
        $helper = $userRepository->findOneBy(['email'=>$this->getUser()->getUsername()]);
        return $this->render('user_controllers/disponibilite/index.html.twig', [
            'disponibilites' => $disponibiliteRepository->getHelperDispMeet($helper),
        ]);
    }

    /**
     * @Route("/helper/{id}", name="helper_disponibilite_index", methods={"GET"})
     */
    public function getdisps(Users $helper, DisponibiliteRepository $disponibiliteRepository, UsersRepository $userRepository, SerializerInterface $serializer): Response
    {
        $disps = $disponibiliteRepository->getHelperDispMeet($helper);
        $res = $serializer->serialize($disps, 'json', ['groups'=>'meet:new']);
        if($res!=null){
            return new JsonResponse(array(
                'status' => 'OK',
                'data' => $res),
                200);
        }
        return new JsonResponse(array(
            'status' => 'ERROR',
            'message'=>"fetch error"),
            500);
    }


    /**
     * @Route("/new/{id}", name="mobile_user_disponibilite_new", methods={"GET","POST"})
     */
    public function new(Request $request, Users $helper, UsersRepository $userRepository): Response
    {
        $disponibilite = new Disponibilite();
        $start = new \DateTime( $request->get('start'));
        $finish = new \DateTime( $request->get('finish'));


        $disponibilite->setHelperid($helper);
        $disponibilite->setEtat(0);
        $disponibilite->setDatedeb($start);
        $disponibilite->setDatefin($finish);

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($disponibilite);
            $entityManager->flush();
        return new JsonResponse("Disponibilite Created", 200);

    }

    /**
     * @Route("/{id}/details", name="mobile_user_disponibilite_show", methods={"GET"})
     */
    public function show(Disponibilite $disponibilite): Response
    {
        return $this->render('user_controllers/disponibilite/show.html.twig', [
            'disponibilite' => $disponibilite,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="mobile_user_disponibilite_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Disponibilite $disponibilite): Response
    {
        $start = new \DateTime( $request->get('start'));
        $finish = new \DateTime( $request->get('finish'));
        $disponibilite->setDatedeb($start);
        $disponibilite->setDatefin($finish);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($disponibilite);
        $entityManager->flush();
        return new JsonResponse("Disponibilite Updated", 200);

    }

    /**
     * @Route("/{id}", name="mobile_user_controllers_disponibilite_delete", methods={"GET","POST"})
     */
    public function delete(Disponibilite $disponibilite): Response
    {
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($disponibilite);
        $entityManager->flush();
        return new JsonResponse("Disponibilite Deleted", 200);
    }
}
