<?php

namespace App\Controller\AdminControllers;

use App\Entity\Suggestion;
use App\Form\SuggestionType;
use App\Repository\SuggestionRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;

/**
 * @Route("/admin/suggestion")
 */
class SuggestionAdminController extends AbstractController
{
    /**
     * @Route("/", name="admin_controllers_suggestion_admin_index", methods={"GET"})
     */
    public function index(SuggestionRepository $suggestionRepository, Request $request, PaginatorInterface $paginator): Response
    {
        $donnees = $this->getDoctrine()->getRepository(Suggestion::class)->findAll();

        $suggestions = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            2 // Nombre de résultats par page
        );

        return $this->render('admin_controllers/suggestion_admin/index.html.twig', [
            'suggestions' => $suggestions,
        ]);

    }



    /**
     * @Route("/{Id_Sugg}", name="admin_controllers_suggestion_admin_show", methods={"GET","POST"})
     */
    public function show(Suggestion $suggestion, Request $request): Response
    {
        $form = $this->createForm(SuggestionType::class, $suggestion);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid())
        {
            $this->getDoctrine()->getManager()->flush();
            return $this->redirectToRoute(admin_controllers_suggestion_admin_index);
        }
        return $this->render('admin_controllers/suggestion_admin/show.html.twig', [
            'suggestion' => $suggestion,
            'form'=>$form->createView(),
        ]);
    }

    /**
     * @Route("/triId", name="triId")
     */

    public function TriId(Request $request)
    {
        $em = $this->getDoctrine()->getManager();

        $query = $em->createQuery(
            'SELECT Sugg FROM App\Entity\Suggestion Sugg 
            ORDER BY Rec.Id_Sugg'
        );
        $rep = $query->getResult();

        return $this->render('admin_controllers/suggestion_admin/show.html.twig',
            array('Suggestion' => $rep));

    }
    /**
     * @Route("/SuggAdminToPDF", name="SuggAdminToPDF", methods={"GET"})
     */
    public function SuggAdminToPDF(SuggestionRepository $suggestionRepository):Response
    {
        $pdfOptions=new Options();
        $pdfOptions->set('defaultFont','Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);
        $Suggestion = $suggestionRepository->findAll();

        return $this->render('admin_controllers/suggestion_admin/index.html.twig',
        ['Suggestion' => $Suggestion,]);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('admin_controllers/suggestion_admin/index.html.twig',
            ['Suggestion' => $Suggestion,]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);
         // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');
        // Render the HTML as PDF
        $dompdf->render();
        // Output the generated PDF to Browser (force download)
        $dompdf->stream("Suggestion.pdf", [
            "Attachment" => true
        ]);

    }

}
