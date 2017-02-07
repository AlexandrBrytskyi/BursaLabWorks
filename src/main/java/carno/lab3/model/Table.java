package carno.lab3.model;


import java.util.HashMap;
import java.util.Map;

public class Table {

    private static Map<String, TableElement> elements = new HashMap<String, TableElement>() {{

        put("000000", new TableElement("Гіпотетично", ""));
        put("000001", new TableElement("Гіпотетично", ""));
        put("000010", new TableElement("Гіпотетично", ""));
        put("000011", new TableElement("Гіпотетично", ""));
        put("000100", new TableElement("Гіпотетично", ""));
        put("000101", new TableElement("Гіпотетично", ""));
        put("000110", new TableElement("Гіпотетично", ""));
        put("000111", new TableElement("Гіпотетично", ""));
        put("001000", new TableElement("Гіпотетично", ""));
        put("001001", new TableElement("Гіпотетично", ""));
        put("001010", new TableElement("Гіпотетично", ""));
        put("001011", new TableElement("Гіпотетично", ""));
        put("001100", new TableElement("Гіпотетично", ""));
        put("001101", new TableElement("Гіпотетично", ""));
        put("001110", new TableElement("Гіпотетично", ""));
        put("001111", new TableElement("Гіпотетично", ""));
        put("110110", new TableElement("Існує", "Системи з ансамблем процессорів: PEPE"));
        put("010001", new TableElement("Гіпотетично", ""));
        put("010010", new TableElement("Гіпотетично", ""));
        put("010011", new TableElement("Гіпотетично", ""));
        put("010101", new TableElement("Гіпотетично", ""));
        put("010110", new TableElement("Гіпотетично", ""));
        put("010111", new TableElement("Гіпотетично", ""));
        put("010101", new TableElement("Існує", "Асоціативні системи: STARAN, RADCAP, ALAP, ECAM"));
        put("011001", new TableElement("Існує", "Асоціативні системи: ALAP, ECAM"));
        put("011010", new TableElement("Не існує", "Неможлива реалізація"));
        put("011011", new TableElement("Не існує", "Неможлива реалізація"));
        put("011100", new TableElement("Гіпотетично", ""));
        put("011101", new TableElement("Гіпотетично", ""));
        put("011110", new TableElement("Гіпотетично", ""));
        put("011111", new TableElement("Гіпотетично", ""));
        put("111000", new TableElement("Не існує", "Неможлива реалізація"));
        put("111001", new TableElement("Не існує", "Неможлива реалізація"));
        put("110010", new TableElement("Існує", "Матричні системи з векторним потоком даних: ILLIAC IV, \nILLIAC III"));
        put("111010", new TableElement("Існує", "Матричні системи з векторним потоком даних: SOLOMON I, SOLOMON II"));
        put("111011", new TableElement("Не існує", "Неможлива реалізація"));
        put("111100", new TableElement("Не існує", "Неможлива реалізація"));
        put("111101", new TableElement("Не існує", "Неможлива реалізація"));
        put("111110", new TableElement("Не існує", "Неможлива реалізація"));
        put("111111", new TableElement("Не існує", "Неможлива реалізація"));
        put("101000", new TableElement("Гіпотетично", ""));
        put("101001", new TableElement("Гіпотетично", ""));
        put("101010", new TableElement("Гіпотетично", ""));
        put("101011", new TableElement("Гіпотетично", ""));
        put("101100", new TableElement("Гіпотетично", ""));
        put("101101", new TableElement("Гіпотетично", ""));
        put("101110", new TableElement("Гіпотетично", ""));
        put("101111", new TableElement("Гіпотетично", ""));
        put("100001", new TableElement("Гіпотетично", ""));
        put("100010", new TableElement("Гіпотетично", ""));
        put("100011", new TableElement("Гіпотетично", ""));
        put("100100", new TableElement("Гіпотетично", ""));
        put("100101", new TableElement("Гіпотетично", ""));
        put("100110", new TableElement("Гіпотетично", ""));
        put("100111", new TableElement("Гіпотетично", ""));
        put("110001", new TableElement("Гіпотетично", ""));
        put("110011", new TableElement("Гіпотетично", ""));
        put("110001", new TableElement("Існує", "Однорідні багатомашинні системи: Минск 222, ВК-1010, PDP-11, \nМ4030, М400б М6000, М7000"));
        put("110000", new TableElement("Існує", "Неоднорідні багатомашинні системи: Днепр2, М-220, МИР-1, СМ-1, СМ-2, \nБОСМ-6, МИР-2, КЛАСС"));
        put("110110", new TableElement("Існує", "Магістральні системи: STAR 100, ASC, CRAY 1, \nAMDAHL 479V/6, CDC6600, CDC7600, IBM 360/91, \nIBM 360/195"));
        put("110111", new TableElement("Існує", "Однорідні багатопроцесорні системи: IBM 360/67 MP, IBM 360/65 MP, \nIBM 370/158 MP, IBM 370/168 MP, Ramo-Woold-Ridge RW400, \nBurroughs D825, CDC 3600, Philco 213, Univac LARC"));
        put("110101", new TableElement("Існує", "Неоднорідні багатопроцесорні системи: Machines Bull GAMMA60, IBM STRETCH, \n"));
        put("110110", new TableElement("Існує", "Неоднорідні багатопроцесорні системи: PILOT, GUS, Эльбрус-1, Эльбрус-2, DEC System10, HISS 6000, Symbol"));
        put("110100", new TableElement("Гіпотетично", ""));


//        put("000000", new TableElement(true, "Будь-який звичайний процесор"));
//        put("000001", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000010", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000011", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000100", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000101", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000110", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("000111", new TableElement(false, "Невірна конфігурація для звичайного процессора"));
//        put("001000", new TableElement(true, "Будь-який однорозрядний процессор"));
//        put("001001", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001010", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001011", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001100", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001101", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001110", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("001111", new TableElement(false, "Невірна конфігурація для однорозрядного процессора"));
//        put("010000", new TableElement(true, "Системи з ансамблем процессорів: PEPE"));
//        put("010001", new TableElement(false, "Невірна конфігурація системи з ансамблем процессорів"));
//        put("010010", new TableElement(false, "Невірна конфігурація системи з ансамблем процессорів"));
//        put("010011", new TableElement(false, "Невірна конфігурація системи з ансамблем процессорів"));
//        put("010100", new TableElement(true, "Матричні системи з векторним потоком даних: ILLIAC IV, \nILLIAC III, SOLOMON I, SOLOMON II"));
//        put("010101", new TableElement(false, "Невірна конфігурація для матричної системи з векторним потоком даних"));
//        put("010110", new TableElement(false, "Невірна конфігурація для матричної системи з векторним потоком даних"));
//        put("010111", new TableElement(false, "Невірна конфігурація для матричної системи з векторним потоком даних"));
//        put("011000", new TableElement(true, "Асоціативні системи: STARAN, RADCAP, ALAP, ECAM"));
//        put("011001", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011010", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011011", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011100", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011101", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011110", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("011111", new TableElement(false, "Невірна конфігурація для асоціативної системи"));
//        put("111000", new TableElement(false, "Неможлива реалізація"));
//        put("111001", new TableElement(false, "Неможлива реалізація"));
//        put("111010", new TableElement(false, "Неможлива реалізація"));
//        put("111011", new TableElement(false, "Неможлива реалізація"));
//        put("111100", new TableElement(false, "Неможлива реалізація"));
//        put("111101", new TableElement(false, "Неможлива реалізація"));
//        put("111110", new TableElement(false, "Неможлива реалізація"));
//        put("111111", new TableElement(false, "Неможлива реалізація"));
//        put("101000", new TableElement(false, "Неможлива реалізація"));
//        put("101001", new TableElement(false, "Неможлива реалізація"));
//        put("101010", new TableElement(false, "Неможлива реалізація"));
//        put("101011", new TableElement(false, "Неможлива реалізація"));
//        put("101100", new TableElement(false, "Неможлива реалізація"));
//        put("101101", new TableElement(false, "Неможлива реалізація"));
//        put("101110", new TableElement(false, "Неможлива реалізація"));
//        put("101111", new TableElement(false, "Неможлива реалізація"));
//        put("100000", new TableElement(true, "Магістральні системи: STAR 100, ASC, CRAY 1, \nAMDAHL 479V/6, CDC6600, CDC7600, IBM 360/91, \nIBM 360/195"));
//        put("100001", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100010", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100011", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100100", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100101", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100110", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("100111", new TableElement(false, "Невірна конфігурація магістральної системи"));
//        put("110000", new TableElement(true, "Однорідні багатомашинні системи: Минск 222, ВК-1010, PDP-11, \nМ4030, М400б М6000, М7000"));
//        put("110001", new TableElement(false, "Невірна конфігурація для багатомашинної системи"));
//        put("110010", new TableElement(true, "Неоднорідні багатомашинні системи: Днепр2, М-220, МИР-1, СМ-1, СМ-2, \nБОСМ-6, МИР-2, КЛАСС"));
//        put("110011", new TableElement(false, "Невірна конфігурація для багатомашинної системи"));
//        put("110101", new TableElement(true, "Однорідні багатопроцесорні системи: IBM 360/67 MP, IBM 360/65 MP, \nIBM 370/158 MP, IBM 370/168 MP, Ramo-Woold-Ridge RW400, \nBurroughs D825, CDC 3600, Philco 213, Univac LARC"));
//        put("110110", new TableElement(false, "Невірна конфігурація для багатомашинної системи"));
//        put("110100", new TableElement(false, "Невірна конфігурація для багатомашинної системи"));
//        put("110111", new TableElement(true, "Неоднорідні багатопроцесорні системи: Machines Bull GAMMA60, IBM STRETCH, \nPILOT, GUS, Эльбрус-1, Эльбрус-2, DEC System10, HISS 6000, Symbol"));

    }};

    public static Map<String, TableElement> getElements() {
        return elements;
    }

    public static TableElement getElementByCode(String code) {
        return elements.get(code);
    }

}
