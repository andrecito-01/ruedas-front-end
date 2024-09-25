package pe.edu.cibertec.ruedas_front_end.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.ruedas_front_end.ViewModel.PlacaModel;
import pe.edu.cibertec.ruedas_front_end.dto.PlacaRequestDTO;
import pe.edu.cibertec.ruedas_front_end.dto.PlacaResponseDTO;

@Controller
@RequestMapping("/search")
public class PlacaController {



    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/Home")
    public String Home(Model model){

        PlacaModel placaModel=new PlacaModel("00","","");
        model.addAttribute("placaModel",placaModel);


        return "Home";
    }



    @PostMapping("/Acces")
    public String Acces(@RequestParam("Placa")String Placa, Model model) {


        if (Placa == null || Placa.trim().length() == 0) {

            PlacaModel placaModel = new PlacaModel("01", "Debe Ingresar una Placa Correcta", "");
            model.addAttribute("placaModel", placaModel);
            return "Home";
        }




        try {


            String endpoint="http://localhost:8081/buscador/info";

            PlacaRequestDTO placaRequestDTO=new PlacaRequestDTO(Placa);
            PlacaResponseDTO placaResponseDTO=restTemplate.postForObject(endpoint,placaRequestDTO,PlacaResponseDTO.class);


            if(placaRequestDTO!=null){

PlacaResponseDTO placaResponsedto=new PlacaResponseDTO(placaResponseDTO.Marca(),placaResponseDTO.Modelo(),placaResponseDTO.NrAsiento(),
        placaResponseDTO.Precio(),placaResponseDTO.Color());
model.addAttribute("placadto",placaResponsedto);

return  "Page";

            }else {
                PlacaModel placaModel = new PlacaModel("01", "Debe Ingresar una Placa Correcta", "k");
                model.addAttribute("placaModel", placaModel);
                return "Home";
            }



        }catch (Exception e){
            PlacaModel placaModel = new PlacaModel("01", "Debe Ingresar una Placa Correcta", "");
            model.addAttribute("placaModel", placaModel);
            return "Home";
        }

    }
    }
