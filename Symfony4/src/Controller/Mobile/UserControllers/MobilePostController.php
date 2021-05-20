<?php

namespace App\Controller\Mobile\UserControllers;

use App\Entity\Post;
use App\Form\Post1Type;
use App\Repository\CommentRepository;
use App\Repository\PostRepository;
use App\Repository\UsersRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Component\Serializer\Encoder\JsonEncode;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Symfony\Component\Serializer\SerializerInterface;

/**
 * @Route("/mobile/post")
 */
class MobilePostController extends AbstractController
{


    /**
     * @Route("/getposts", name="getposts_mobile")
     */
    public function getPosts(PostRepository $postRepository,SerializerInterface $serializer): Response
    {
        $posts =$postRepository->findAllPostsOrderedByNewest();

        $res = $serializer->serialize($posts, 'json', ['groups'=>'post:index']);
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
     * @Route("/new/{problem}/{module}", name="addpost_mobile", methods={"POST"})
     */
    public function new($problem,$module,Request $request,SerializerInterface $serializer,EntityManagerInterface $entityManager,UsersRepository $usersRepository): Response
    {
       // $content=$request->getContent();
        //$parametersAsArray = json_decode($content, true);
        //dd($parametersAsArray,$content);
        //$owner=$usersRepository->findOneBy(['id'=>$parametersAsArray['owner']]);
       // $parametersAsArray['owner']=$owner;

        $post= new Post();
        $thisDate= new \DateTime('now');
        $post->setTimestamp($thisDate);
        //$post->setOwner($owner);
        $post->setModule($module);


        $post->setProblem($problem);

        $entityManager->persist($post);
        $entityManager->flush();

        return new JsonResponse('post added successfully !');
    }





    /**
     * @Route("/{id}", name="user_controllers_post_show", methods={"GET"})
     */
    public function show($id,Post $post,EntityManagerInterface $em): Response
    {
        $post = $em->getRepository(Post::class)
            ->findOneBy([
                'id'=> $id,
            ]);
        $comments = $post->getComments();
        return $this->render('user_controllers/post/show.html.twig',[
            'post' => $post,
            'comments' => $comments,
        ]);
    }

    /**
     * @Route("/{id}/edit/{problem}", name="updatepost_mobile", methods={"POST"})
     */
    public function edit($id,$problem,Request $request, Post $post,EntityManagerInterface $em,SerializerInterface $serializer): Response
    {
        $repo = $em->getRepository(Post::class);
        $post = $repo ->findOneBy(['id'=>$id]);
        $post->setProblem($problem);

        $em->persist($post);
        $em->flush();

        $json = $serializer->serialize($post,'json',['groups'=>'post:index']);
        dd($post);
        return new JsonResponse('Post Updated');

    }

    /**
     * @Route("/delete/{id}", name="deletepost_mobile")
     */
    public function delete($id,Request $request,EntityManagerInterface $em): Response
    {
        $repo= $em->getRepository(Post::class);
        $post=$repo->findOneBy(
            ['id' => $id]
        );

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($post);
        $entityManager->flush();


        return new JsonResponse(['oof'=>"post deleted"]);
    }

}
