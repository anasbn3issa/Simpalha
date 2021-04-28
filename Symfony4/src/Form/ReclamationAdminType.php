<?php

namespace App\Form;

use App\Entity\Reclamation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;


class ReclamationAdminType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('Idreportee', TextType::class,[
                'label'=>'idreportee',
                'attr'=>['placeholder'=>'idreportee']])

            ->add('Idreported', TextType::class,[
                'label'=>'idreported',
                'attr'=>['placeholder'=>'idreported']])

            ->add('description', TextType::class,[
                'label'=>'description',
                'attr'=>['placeholder'=>'description']])

            ->add('FileSelected', FileType::class, [
            'label'=>'Image',
            'mapped'=> false])

            ->add('Record', TextType::class, [
                'label'=>'Record',
                'attr'=>['placeholder'=>'Record']])

            ->add('daterec', DateType::class, [
                'label' => 'date claim'])

            ->add('dateresolution', DateType::class, [
                'label' => 'date resolution'])

            ->add('Status',ChoiceType::class,[
                'choices'=> array(
                    1=>'Validate',
                    0=>'NotValidate',

                ),
            ])

        ;
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Reclamation::class,
        ]);
    }
}
