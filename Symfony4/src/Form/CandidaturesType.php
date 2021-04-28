<?php

namespace App\Form;

use App\Entity\Candidatures;
use App\Entity\Users;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class CandidaturesType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('description')
            ->add('Specialty')
            ->add('fichier', FileType::class, ['attr' => ['class' => 'custom-file-input'],
                'label' => 'upload your certifications/report',
                'mapped' => false,
                'required' => false,
            ]);;

    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Candidatures::class,
        ]);
    }
}
