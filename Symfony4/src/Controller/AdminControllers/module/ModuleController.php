<?php

namespace App\Controller\AdminControllers\module;

use App\Entity\Module;
use App\Form\ModuleType;
use App\Repository\ModulesRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;



/**
 * @Route("/module")
 */
class ModuleController extends AbstractController
{
    /**
     * @Route("/", name="module_index", methods={"GET"})
     */
    public function index(Request $request,PaginatorInterface $paginator): Response
    {
        $module = $this->getDoctrine()->getRepository(Module::class)->findAll();
        $pagination = $paginator->paginate(
            $module,
            $request->query->getInt('page', 1), /*page number*/
            7 /*limit per page*/
        );


        return $this->render('admin_controllers/module/index.html.twig',[
            "modules" =>$pagination,
        ]);


       /* return $this->render('module/index.html.twig', [
            'modules' => $modulesRepository->findAll(),
        ]);*/
    }

    /**
     * @Route("/new", name="module_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $module = new Module();
        $form = $this->createForm(ModuleType::class, $module);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($module);
            $entityManager->flush();

            return $this->redirectToRoute('module_index');
        }

        return $this->render('admin_controllers/module/new.html.twig', [
            'module' => $module,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="module_show", methods={"GET"})
     */
    public function show(Module $module): Response
    {
        return $this->render('admin_controllers/module/show.html.twig', [
            'module' => $module,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="module_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Module $module): Response
    {
        $form = $this->createForm(ModuleType::class, $module);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('module_index');
        }

        return $this->render('admin_controllers/module/edit.html.twig', [
            'module' => $module,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/{id}", name="module_delete", methods={"POST"})
     */
    public function delete(Request $request, Module $module): Response
    {
        if ($this->isCsrfTokenValid('delete'.$module->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($module);
            $entityManager->flush();
        }

        return $this->redirectToRoute('module_index');
    }

    ///////////////////////Recherche av /////////////
    /**
     *@Route("searchajax", name="ajaxxsearch")
     */
    public function searchAction(Request $request)
    {
        $em=$this->getDoctrine()->getManager();
        $requestString = $request->get('q');
        $modules = $em->getRepository(Module::class)->findEntitiesByString($requestString);
        if(!$modules)
        {
            $result['modules']['error']=" Module Not FOUND! :( ";

        }else{
            $result['modules']=$this->getRealEntities($modules);
        }
        return new Response(json_encode($result));

    }
    public function getRealEntities($modules){
        foreach ($modules as $modules){
            $realEntities[$modules->getId()] = [$modules->getName()];
        }
        return $realEntities;
    }

    /// ////////////////end recherche av//////////////


}
