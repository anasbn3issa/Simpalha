<?php

namespace App\Controller\UserControllers;

use App\Repository\QuizzRepository;
use App\Repository\QuizzResultRepository;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\IsGranted;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\UX\Chartjs\Builder\ChartBuilder;
use Symfony\UX\Chartjs\Builder\ChartBuilderInterface;
use Symfony\UX\Chartjs\Model\Chart;

class QuizChartController extends AbstractController
{
    /**
     * @Route("/quiz/{id}/progression/chart", name="quiz_chart")
     * @IsGranted("ROLE_USER")
     */
    public function index($id, QuizzRepository $quizzRepository, QuizzResultRepository  $quizzResultRepository, ChartBuilderInterface $chartBuilder): Response
    {
        $quiz = $quizzRepository->findOneBy(['id'=>$id]);
        $quizResults = $quizzResultRepository->findAllBeforeSelectedTimeAsc($quiz, new \DateTime());

        dump($quizResults);

        $labels = [];
        $data = [];

        foreach($quizResults as $quizResult){
            $labels[] = $quizResult->getResultDate()->format('d/m/Y');
            $data[] = $quizResult->getResult();
        }

        dump($data);
        dump($labels);

        $chart = $chartBuilder->createChart(Chart::TYPE_LINE);
        $chart->setData([
            'labels' => $labels,
            'datasets' => [
                [
                    'label' => 'Result',
                    'backgroundColor' => 'rgb(255, 99, 132)',
                    'borderColor' => 'rgb(255, 99, 132)',
                    'data' => $data,
                ],
            ],
        ]);

        $chart->setOptions([/* ... */]);


        return $this->render('user_controllers/quiz_chart/index.html.twig', [
            'chart' => $chart,
            'quiz' => $quiz
        ]);
    }
}
