<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Feedback;
use App\Entity\Meet;
use App\Entity\Users;
use App\Form\FeedbackType;
use App\Form\MeetType;
use App\Repository\DisponibiliteRepository;
use App\Repository\MeetRepository;
use App\Repository\UsersRepository;
use Ramsey\Uuid\Uuid;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("mobile/meet")
 */
class MobileMeetController extends AbstractController
{


    /**
     * @Route("/getmeetbydisp", name="getmeetbydisp", methods={"GET"})
     */
    public function getmeetbydisp(Request $request, MeetRepository $meetRepository, DisponibiliteRepository $disponibiliteRepository, NormalizerInterface $normalizer): Response
    {
        $filter = $request->query->get('id');
        $dis = $disponibiliteRepository->findOneBy(['id'=>$filter]);
        $meet = $meetRepository->findOneBy(['disponibilite'=>$dis]);
        if($meet != null) {
            $json_meets = $normalizer->normalize($meet, 'json', ['groups'=>'meet:search']);
            // Send all this back to client
            return new JsonResponse(array(
                'status' => 'OK',
                'data' => $json_meets),
                200);
        }
        return new JsonResponse(array(
            'status' => 'ERROR',
            'data' => 'not found'),
            200);
    }

    /**
     * @Route("/student/{id}", name="mobile_meet_index", methods={"GET"})
     */
    public function index(Users $student, MeetRepository $meetRepository, SerializerInterface $serializer)
    {
        $meets = $meetRepository->findByUser($student);
        $res = $serializer->serialize($meets, 'json', ['groups'=>'meet:student']);
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
     * @Route("/helper/{id}", name="mobile_helper_meet_index", methods={"GET"})
     */
    public function helperIndex(Users $helper, MeetRepository $meetRepository, SerializerInterface $serializer)
    {
        $meets = $meetRepository->findByHelper($helper);
        $res = $serializer->serialize($meets, 'json', ['groups'=>'meet:student']);
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
     * @Route("/new/{id}", name="mobile_meet_new", methods={"GET","POST"})
     */
    public function new(Request $request, Users $user, UsersRepository $userRepository, DisponibiliteRepository $disponibiliteRepository): Response
    {

        $student = $userRepository->findOneBy(['id'=>$request->get('student_id')]);
        $disponibilite = $disponibiliteRepository->findOneBy(['id'=>$request->get('disponibilite_id')]);

        if($student == null | $disponibilite == null)
            return new JsonResponse("Error", 500);
        $meet = new Meet();
        $meet->setEtat(0);
        $meet->setIdHelper($user);
        $meet->setSpecialite($user->getSpecialite());
        $meet->setDisponibilite($disponibilite);
        $meet->setIdStudent($student);
        $em = $this->getDoctrine()->getManager();
        $em->persist($meet);
        $disponibilite->setEtat(1);
        $em->persist($disponibilite);
        $em->flush();
        return new JsonResponse("Meet Created", 200);
    }

    /**
     * @Route("/{id}/edit", name="mobile_meet_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Meet $meet, DisponibiliteRepository $disponibiliteRepository): Response
    {
        $olddisp = $meet->getDisponibilite();
        $newdisp = $disponibiliteRepository->findOneBy(["id"=>$request->get("disponibilite_id")]);
            $entityManager = $this->getDoctrine()->getManager();

            $olddisp->setEtat(0);
            $newdisp->setEtat(1);

            $meet->setDisponibilite($newdisp);

            $entityManager->persist($olddisp);
            $entityManager->persist($meet);
            $this->getDoctrine()->getManager()->flush();
        return new JsonResponse("Meet Updated", 200);

    }

    /**
     * @Route("/{id}", name="mobile_meet_delete", methods={"GET","POST"})
     */
    public function delete(Request $request, Meet $meet): Response
    {
            $entityManager = $this->getDoctrine()->getManager();

            $disponibilite = $meet->getDisponibilite();
            $disponibilite->setEtat(0);

            $entityManager->persist($disponibilite);

            $entityManager->remove($meet);
            $entityManager->flush();

            return new JsonResponse("Meet Deleted", 200);
    }
}
