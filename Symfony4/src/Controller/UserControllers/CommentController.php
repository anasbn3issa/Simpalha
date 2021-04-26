<?php

namespace App\Controller\UserControllers;

use App\Entity\Comment;
use App\Entity\Post;
use App\Form\CommentType;
use App\Repository\CommentRepository;
use Psr\Log\LoggerInterface;
use Doctrine\ORM\EntityManagerInterface;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

/**
 * @Route("/comment")
 */
class CommentController extends AbstractController
{

    /**
     * @Route("/post/{id}/comments", name="comments_list")
     * @IsGranted("ROLE_USER")
     */
    public function list($id,Request $request, EntityManagerInterface $em,CommentRepository $commentRepository)
    {

        $post = $em->getRepository(Post::class)
            ->findOneBy([
                'id'=> $id,
            ]);
        //$comments = $post->getComments();

        $comments =$commentRepository->findAllCommentsSortedByUpvotes($post);
        $comment = new Comment();

        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $comment = $form->getData();
            $comment->setPost($post);
            $comment->setOwner($this->getUser());
            $post->setComment($comment);

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($comment);
            $entityManager->flush();

            $this->addFlash('success','thank you for helping the community ');
            return $this->redirectToRoute('comments_list',[
                'id' => $id
            ]);
        }

        return $this->render('user_controllers/comment/index.html.twig', [
            'form' => $form->createView(),
            'comment' => $comment,
            'comments' => $comments,
            'post' => $post,

        ]);



    }
    /**
     * @Route("/post/{id}/comment/upvote", name="comment_upvote", methods={"POST","GET"})
     * @IsGranted("ROLE_USER")
     */
    public function togglePostHeart($id,Comment $comment, LoggerInterface $logger, Request $request,EntityManagerInterface $em,CommentRepository $commentRepository)
    {
        $comment->incrementUpvotesCount();
        $em->flush();

        $logger->info('Comment is being hearted!');

        //return path('comment_upvote');
        $idpost =$comment->getPost()->getId();
        $this->list($idpost,$request,$em,$commentRepository);
        return $this->redirectToRoute('comments_list',array(
            'id'=>$idpost
        ));

    }
    /**
     * @Route("/{id}", name="user_controllers_comment_show", methods={"GET"})
     */
    public function show(Comment $comment): Response
    {
        return $this->render('user_controllers/comment/show.html.twig', [
            'comment' => $comment,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="user_controllers_comment_edit", methods={"GET","POST"})
     * @IsGranted("ROLE_USER")
     */
    public function edit(Request $request, Comment $comment): Response
    {
        $form = $this->createForm(CommentType::class, $comment);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('user_controllers_comment_index');
        }

        return $this->render('user_controllers/comment/edit.html.twig', [
            'comment' => $comment,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="user_controllers_comment_delete", methods={"POST"})
     */
    public function delete(Request $request, Comment $comment): Response
    {
        if ($this->isCsrfTokenValid('delete'.$comment->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($comment);
            $entityManager->flush();
        }

        return $this->redirectToRoute('user_controllers_comment_index');
    }

    /**
     * @Route("/mark_as_solution/{id}", name="user_mark_as_solution",methods={"POST","GET"})
     * @IsGranted("ROLE_USER")
     */
    public function markAsSolution(Request $request,Comment $comment,EntityManagerInterface $em,LoggerInterface $logger): Response
    {
        $post=$comment->getPost();
        $post->setSolution($comment);
        $post->setStatus("SOLVED");
        $em->flush();
        $logger->info('Comment is being marked as solution!');
        $this->addFlash('success','Glad to help');
        return $this->redirectToRoute('comments_list',array(
            'id'=>$post->getId()
        ));
    }

}
