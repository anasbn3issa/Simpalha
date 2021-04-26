<?php

namespace App\Controller\AdminControllers;

use App\Entity\Reclamation;
use App\Form\ReclamationAdminType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\DependencyInjection\ContainerInterface;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use CMEN\GoogleChartsBundle\CMENGoogleChartsBundle;
use CMEN\GoogleChartsBundle\GoogleCharts\Charts\PieChart;
use Knp\Component\Pager\PaginatorInterface;


/**
 * @Route("/admin/controllers/reclamation/admin")
 */
class ReclamationAdminController extends AbstractController
{
    /**
     * @Route("/", name="admin_controllers_reclamation_admin_index", methods={"GET"})
     */
    public function index(ReclamationRepository $reclamationRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $donnees = $this->getDoctrine()->getRepository(Reclamation::class)->findAll();

        $reclamations = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            2 // Nombre de résultats par page
        );

        return $this->render('admin_controllers/reclamation_admin/index.html.twig', [
            'reclamations' => $reclamations,
        ]);
    }

    /**
     * @Route("/{id}", name="admin_controllers_reclamation_admin_show", methods={"GET","POST" })
     */
    public function show(Reclamation $reclamation, Request $request): Response
    {
        $form = $this->createForm(ReclamationAdminType::class, $reclamation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();
            return $this->redirectToRoute(admin_controllers_reclamation_admin_index);
        }
        return $this->render('admin_controllers/reclamation_admin/show.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);


    }


    /**
     * @Route("/{id}/editRecAdmin", name="admin_controllers_reclamation_admin_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Reclamation $reclamation): Response
    {
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $image = $request->files->get('reclamation')['FileSelected'];
            $uploads_directory = $this->getParameter('kernel.root_dir') . '/../public/img';
            $filename = md5(uniqid()) . '.' . $image->guessExtension();
            $image->move(
                $uploads_directory,
                $filename
            );

            $reclamation->setFileselected($filename);
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('admin_controllers_reclamation_admin_index');
        }

        return $this->render('admin_controllers/reclamation_admin/edit.html.twig', [
            'reclamation' => $reclamation,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/Validate/{id}", name="admin_controllers_reclamation_admin_validate", methods={"GET","POST"})
     */
    public function validate($id, ReclamationRepository $repository)
    { $reclamation = $repository->find($id);
     $reclamation->setStatus(1);
     $entityManager = $this->getDoctrine()->getManager();
     $entityManager->flush();
     return $this->redirectToRoute("admin_controllers_reclamation_admin_index");
     return $this->render("reclamation_admin/show.html.twig");
    }

    /**
     * @Route("/Supp/{id}", name="admin_controllers_reclamation_admin_delete")
     */
    public function delete($id, ReclamationRepository $repository)
    {
        $reclamation = $repository->find($id);
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($reclamation);
        $entityManager->flush();


        return $this->redirectToRoute('admin_controllers_reclamation_admin_index');
    }


    /**
     * @Route("occurence", name="occurence")
     */
    public function occurence():Response
    {
        $repository = $this->getDoctrine()->getRepository(Reclamation::class);
        $reclamation = $repository->findAll();
        $em = $this->getDoctrine()->getManager();

        $Validate = 0;
        $NotValidate = 0;
        $percV =0;
        $percNV = 0;
        $NBrec=0;
        foreach ($reclamation as $reclamation) {
            $NBrec+=1;
            if ($reclamation->getStatus() == "Validate")  :

                $Validate += 1;
            elseif ($reclamation->getStatus() == "NotValidate"):

                $NotValidate += 1;

            endif;

        }

        $percV = number_format(($Validate/$NBrec)*100,2);
        $percNV = number_format(($NotValidate/$NBrec)*100, 2);

        return new Response('percentage claims Validates : '.$percV.' %', 'percentage claims not validates: ' .$percNV. '%');


    }

    /**
     * @Route("/change_locale/{locale}", name="change_locale")
     */
    public function changeLocale($locale, Request $request)
    {
        // On stocke la langue dans la session
        $request->getSession()->set('_locale', $locale);

        // On revient sur la page précédente
        return $this->redirect($request->headers->get('referer'));
    }


}
