<?php

namespace App\Controller\AdminControllers;

use App\Entity\Reclamation;
use App\Form\ReclamationAdminType;
use App\Repository\ReclamationRepository;
use CMEN\GoogleChartsBundle\GoogleCharts\Options\PieChart\PieSlice;
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
 * @Route("/admin/reclamation")
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
        $validated = $reclamationRepository->findByStatus(1);
        $all = $reclamationRepository->findAll();
        $rejected = $reclamationRepository->findByStatus(0);

        $pieChart = new PieChart();
        $pieChart->getData()->setArrayToDataTable(
            [
                ['Label', 'Percentage'],
                ['validate', count($rejected)],
                ['Not validate', count($validated)]
            ]
        );
        $pieChart->getOptions()->setTitle('Percentage Claims');
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(500);
        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(400);
        $pieChart->getOptions()->getTooltip()->setTrigger('focus');
        $pieChart->getOptions()->getLegend()->setPosition('top');
        $pieChart->getOptions()->setPieSliceText('Label');
        return $this->render('admin_controllers/reclamation_admin/index.html.twig', [
            'reclamations' => $reclamations,
            'pieChart' => $pieChart
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
    {
        $reclamation = $repository->find($id);
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
     * @Route("/statistics",name="statistics")
     */
    public function statistics(ReclamationRepository $ReclamationRepository)
    {
        //$liste=$this->verif($reclamationRepository,$donnees);

        $list1 = $ReclamationRepository->calcul(1);
        $total1 = 1;
        foreach ($list1 as $row) {
            $total1++;
        }

        $list2 = $ReclamationRepository->calcul(0);
        $total2 = 0;
        foreach ($list2 as $row) {
            $total2++;
        }
        $pieChart = new PieChart();
        $pieChart->getData()->setArrayToDataTable(
            [
                ['Pac Man', 'Percentage'],
                ['', 75],
                ['', 25]
            ]
        );
        $pieChart->getOptions()->getLegend()->setPosition('none');
        $pieChart->getOptions()->setPieSliceText('none');
        $pieChart->getOptions()->setPieStartAngle(135);

        $pieSlice1 = new PieSlice();
        $pieSlice1->setColor('yellow');
        $pieSlice2 = new PieSlice();
        $pieSlice2->setColor('transparent');
        $pieChart->getOptions()->setSlices([$pieSlice1, $pieSlice2]);

        $pieChart->getOptions()->setHeight(500);
        $pieChart->getOptions()->setWidth(900);
        $pieChart->getOptions()->getTooltip()->setTrigger('none');
        var_dump($pieChart);
        dump($pieChart);
        return $this->render('admin_controllers/reclamation_admin/index.html.twig', array(
                'chart' => $pieChart,
            )
        );
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
