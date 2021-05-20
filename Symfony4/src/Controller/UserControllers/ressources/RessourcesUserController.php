<?php

namespace App\Controller\UserControllers\ressources;

use App\Entity\Ressources;
use App\Repository\RessourcesRepository;
use Knp\Snappy\Pdf;
use Symfony\Bridge\Twig\Extension\HttpFoundationExtension;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Bundle\FrameworkBundle\Templating\Helper\AssetsHelper;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Dompdf\Dompdf;
use Dompdf\Options;
use Knp\Bundle\SnappyBundle\Snappy\Response\PdfResponse;


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
    public function pdf(Ressources $ressource, Request $request) : Response
    {
        $logo =  $this->getParameter("directory")."/images/Simpalha.png";
        $type1 = pathinfo($logo, PATHINFO_EXTENSION);
        $data1 = file_get_contents($logo);
        $base641 = 'data:image/' . $type1 . ';base64,' . base64_encode($data1);
        $logo= $base641;




        $path = $this->getParameter("directory").$ressource->getPath();
        $type = pathinfo($path, PATHINFO_EXTENSION);
        $data = file_get_contents($path);
        $base64 = 'data:image/' . $type . ';base64,' . base64_encode($data);
        $ressource->setPath($base64);
        define("DOMPDF_UNICODE_ENABLED", true);

        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->setIsRemoteEnabled(true);
        $pdfOptions->set('defaultFont', 'Arial');


        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);



        /*
                $path = "http://localhost:8000".$ressource->getPath();
                $type = pathinfo($path, PATHINFO_EXTENSION);
                $data = file_get_contents($path);
                $base64 = 'data:image/' . $type . ';base64,' . base64_encode($data);
                $ressource->setPath($base64);
        */        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('user_controllers/ressources/pdf.html.twig', [
            'ressource' => $ressource,
            'logo' => $logo,
        ]);
        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        $title= $ressource->getTitle()."_".$ressource->getIdr().".pdf";
        // Output the generated PDF to Browser (force download)
        $dompdf->stream($title, [
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


    /**
     * @Route("/pdf2/{idr}", name="pdf2", methods={"GET"})
     */
    public function pepe2(Ressources $ressource,Pdf $knpSnappyPdf): Response
    {

        $html = $this->renderView('user_controllers/ressources/pdf.html.twig',[
            'ressource' => $ressource,
        ]);

        return new PdfResponse(
            $knpSnappyPdf->getOutputFromHtml($html),
            'file.pdf'
        );


    }



}
