<?php

namespace App\Controller\UserControllers;

use App\Entity\Post;
use App\Form\Post1Type;
use App\Repository\CommentRepository;
use App\Repository\PostRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\File\Exception\FileException;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;

/**
 * @Route("/post")
 */
class PostController extends AbstractController
{
    /**
     * @Route("/", name="user_controllers_post_index", methods={"GET"})
     */
    public function index(PostRepository $postRepository): Response
    {
        return $this->render('user_controllers/simpalha_user/index.html.twig', [
            'posts' => $postRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="user_controllers_post_new", methods={"GET","POST"})
     * @IsGranted("ROLE_USER")
     */
    public function new(Request $request): Response
    {
        $post = new Post();
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            // for the upload

            $img=$form->get('imageName')->getData();

            if($img){

                $newFileName= md5(uniqid()).'.'.$img->guessExtension();
            }
            try {
                $img->move(
                    $this->getParameter('postImages'),
                    $newFileName
                );
            } catch (FileException $e) {
                echo $e->getMessage();
            }
            $post->setImageName('/postImages/'.$newFileName);
            $post->setOwner($this->getUser());



            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($post);
            $entityManager->flush();

            $this->addFlash('success','Post Created! Knowledge is power!');
            return $this->redirectToRoute('user_controllers_post_index');
        }

        return $this->render('user_controllers/post/new.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/posts", name="user_controller_post_list")
     */
    public function list(PostRepository $postRepository )
    {
        $posts = $postRepository->findAllPostsOrderedByNewest();

        return $this->render('user_controllers/post/list.html.twig',[
            'posts' => $posts
        ]);
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
     * @Route("/{id}/edit", name="user_controllers_post_edit", methods={"POST"})
     * @IsGranted("ROLE_USER")
     */
    public function edit(Request $request, Post $post,EntityManagerInterface $em): Response
    {
        $form = $this->createForm(Post1Type::class, $post);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            $this->addFlash('success','Post Modified with success!');
            return $this->redirectToRoute('user_controllers_post_index');
        }

        return $this->render('user_controllers/post/edit.html.twig', [
            'post' => $post,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/delete/{id}", name="user_controllers_post_delete")
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

        $this->addFlash('failure','Post Deleted.');

        return $this->redirectToRoute('user_controllers_post_index');
    }

}
