<?php

namespace App\Controller\UserControllers\ressources;

use App\Entity\Ressources;
use App\Repository\RessourcesRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;

class RessourcesUserController extends AbstractController
{/**
 * @Route("/RUser", name="ressourcesUser_index", methods={"GET"})
 */
    public function index(RessourcesRepository $ressourcesRepository): Response
    {
        return $this->render('user_controllers/ressources/indexUser.html.twig', [
            'ressources' => $ressourcesRepository->findAll(),
        ]);
    }

    /**
     * @Route("/RUser/{idr}", name="ressourcesUser_show", methods={"GET"})
     */
    public function show(Ressources $ressource): Response
    {
        return $this->render('user_controllers/ressources/showRessourcesUser.html.twig', [
            'ressource' => $ressource,
        ]);
    }


    /**
     * @Route("/RUser/pdf/{idr}", name="ressource_pdf", methods={"GET"})
     */
    public function pdf(Ressources $ressource) : Response
    {


        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');
        $pdfOptions->set('isRemoteEnabled', TRUE);


        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('user_controllers/ressources/pdf.html.twig', [
            'ressource' => $ressource,
        ]);
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Resource_BY_SIMPALHA.pdf", [
            "Attachment" => true
        ]);


    }

    /**
     * @Route("/pdf/{idr}", name="pdf", methods={"GET"})
     */
    public function pepe(Ressources $ressource): Response
    {
        return $this->render('user_controllers/ressources/pdf.html.twig', [
            'ressource' => $ressource,
        ]);
    }


}
