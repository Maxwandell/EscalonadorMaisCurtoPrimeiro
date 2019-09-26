package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FachadaEscalonador {

	private int quantum;
	private int tick;
	private TipoEscalonador tipoEscalonador;
	private Queue<String> listaProcesso;
	private String rodando, Processo2, Processo1, Processo3;
	private Queue<String> processoBloqueado;
	// private Queue<Integer> tempTicks;
	private String aFinalizar;
	private String aBloquear;
	private int controle, tempoPrafinalizar, tempo, sobra, variavel;
	private List<String> aRetomar;
	private int gato;
	private MinhaFachada status = new MinhaFachada();
	private int myQuantum;


	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		this.myQuantum = 0;
		this.quantum = 3;
		this.controle = 0;
		this.tick = 0;
		this.tipoEscalonador = tipoEscalonador;
		this.listaProcesso = new LinkedList<String>();
		this.processoBloqueado = new LinkedList<String>();
		// this.tempTicks = new LinkedList<Integer>();
		this.aRetomar = new ArrayList<String>();
		this.gato = 0;
		if (tipoEscalonador == null) {
			throw new EscalonadorException();
		}

	}

	public FachadaEscalonador(TipoEscalonador roundrobin, int quantum) {
		this.quantum = quantum;
		this.controle = 0;
		this.tick = 0;
		this.tipoEscalonador = roundrobin;
		this.listaProcesso = new LinkedList<String>();
		this.processoBloqueado = new LinkedList<String>();
		// this.tempTicks = new LinkedList<Integer>();
		this.aRetomar = new ArrayList<String>();
		this.gato = 0;
		if (quantum <= 0) {
			throw new EscalonadorException();
		}
		if (roundrobin == null) {
			throw new EscalonadorException();
		}
	}

	public String getStatus() {
		if (tipoEscalonador == TipoEscalonador.MaisCurtoPrimeiro) {
			return status.getStatusMaisCurtoPrimeiro(tipoEscalonador, rodando, listaProcesso, myQuantum, tick);

		} else {

			String reslt = "";

			reslt += "Escalonador " + this.tipoEscalonador + ";";

			reslt += "Processos: {";

			if (rodando != null) {
				reslt += "Rodando: " + this.rodando;

			}
			if (listaProcesso.size() > 0) {
				if (rodando != null) {
					reslt += ", ";
				}
				reslt += "Fila: " + this.listaProcesso.toString();

			}
			if (processoBloqueado.size() > 0) {
				if (rodando != null) {
					reslt += ", ";
				}
				reslt += "Bloqueados: " + this.processoBloqueado.toString();

			}

			reslt += "};Quantum: " + this.quantum + ";";

			reslt += "Tick: " + this.tick;

			return reslt;
		}
	}

	public void tick() {
		this.tick++;
		
//////////////////////////////////// MAIS CURTO PRIMEIRO /////////////////////////////////////////////////**
		 																								//**
		if (tipoEscalonador == TipoEscalonador.MaisCurtoPrimeiro) {										//**
			if (variavel == 0) {																		//**
				DefinirQuemTaRodando();																    //**
				ProcessosInicioTickZero();																//**
			}																							//**
			if (variavel > 0) {																			//**
				ProcessosAddComTickMaioQueZero();														//**
			}																							//**
		}																								//**
///////////////////////////////// MAIS CURTO PRIMEIRO ////////////////////////////////////////////////////**
			else {

				if (this.rodando == null) {
					if (this.listaProcesso.size() != 0) {
						this.rodando = this.listaProcesso.poll();
						this.controle = this.tick;
					}
				}
				if (aFinalizar != null) {
					if (this.rodando == this.aFinalizar) {
						this.rodando = null;
						if (this.listaProcesso.size() != 0) {
							this.rodando = this.listaProcesso.poll();
							this.controle = this.tick;

						}
					} else {
						this.listaProcesso.remove(aFinalizar);
					}
					this.aFinalizar = null;

				}
				if (aBloquear != null) {
					if (this.rodando == this.aBloquear) {
						this.rodando = null;
						this.processoBloqueado.add(aBloquear);
						if (this.listaProcesso.size() != 0) {
							this.rodando = this.listaProcesso.poll();
							this.controle = this.tick;

						}
					} else {
						this.listaProcesso.remove(aBloquear);
						this.processoBloqueado.add(aBloquear);
					}
					this.aBloquear = null;

				}
				if (this.aRetomar.size() > 0) {
					for (String k : this.aRetomar) {
						this.listaProcesso.add(k);
						this.processoBloqueado.remove(k);
					}
					this.aRetomar.clear();

					if (this.rodando == null) {
						this.rodando = this.listaProcesso.poll();
					}
				}

				// Para trocar de processos
				if (this.listaProcesso.size() > 0) {
					if (this.rodando != null) {
						if (this.gato != 0) {
							this.controle = gato;
							this.gato = 0;
						}
						int temp = this.controle + this.quantum;
						if (temp == this.tick) {
							this.listaProcesso.add(this.rodando);
							this.rodando = this.listaProcesso.poll();
							this.controle = this.tick;

						}
					}

				}
			}

		}

////////////////////////////     MAIS CURTO PRIMEIRO         ///////////////////////////////////**
																							  //**
	protected void DefinirQuemTaRodando() {                                                   //**																								
		if (listaProcesso.contains(this.Processo2)) {										  //**
			this.rodando = this.Processo2;                                                    //**
			listaProcesso.remove(this.rodando);												  //**
		}																					  //**
		if (Processo2 == null) {															  //**
			this.rodando = this.Processo1;													  //**
			listaProcesso.remove(this.rodando);												  //**
		}																					  //**	
	}																						  //**
	protected void ProcessosInicioTickZero() {												  //**
		if (tick > tempoPrafinalizar) {
			if (listaProcesso.size() > 0) {
				rodando = Processo1;
				tempoPrafinalizar += tempo;
				listaProcesso.remove(rodando);
				if (sobra > 0) {
					Processo1 = Processo3;
				}
			}

			else {

				rodando = null;
			}
		}
	}

	protected void ProcessosAddComTickMaioQueZero() {
		if (tick > this.tempoPrafinalizar) {
			if (listaProcesso.size() > 0) {
				this.rodando = this.Processo1;
				this.tempoPrafinalizar += this.tempo;
				listaProcesso.remove(this.rodando);
				if (sobra > 0) {
					Processo1 = Processo3;														 //**
				}																				 //**
			}																					 //**
			else {																				 //**
				this.rodando = null;															 //**
			}                                                                	   				 //**
		}																						 //**
	}																							 //**
/////////////////////////////////       MAIS CURTO PRIMEIRO     ///////////////////////////////////**

	public void adicionarProcesso(String nomeProcesso) {
		if (this.listaProcesso.contains(nomeProcesso) || this.rodando == nomeProcesso) {
			throw new EscalonadorException();
		} else {
			this.listaProcesso.add(nomeProcesso);
			if (this.tick != 0) {
				this.gato = this.tick + 1;
			}
		}

	}

	public void adicionarProcesso(String nomeProcesso, int prioridade) {

		if (tipoEscalonador.equals(escalonadorRoundRobin())) {
			throw new EscalonadorException();
		} else {
			this.listaProcesso.add(nomeProcesso);
			if (this.tick != 0) {
				this.gato = this.tick + 1;
			}
		}
		if (tipoEscalonador == TipoEscalonador.MaisCurtoPrimeiro && prioridade > 0) {
			throw new EscalonadorException();

		}
	}

	public void finalizarProcesso(String nomeProcesso) {
		if (this.rodando == nomeProcesso || this.listaProcesso.contains(nomeProcesso)) {
			this.aFinalizar = nomeProcesso;
		} else {
			throw new EscalonadorException();
		}

	}

	public void bloquearProcesso(String nomeProcesso) {
		if (this.rodando != nomeProcesso) {
			throw new EscalonadorException();
		} else if (this.rodando == nomeProcesso) {
			this.aBloquear = nomeProcesso;
		} else {
			throw new EscalonadorException();
		}

	}

	public void retomarProcesso(String nomeProcesso) {
		// this.aRetomar.add(nomeProcesso);
		/*
		 * if (this.aRetomar.contains(nomeProcesso)) { throw new EscalonadorException();
		 * }else { this.aRetomar.add(nomeProcesso);
		 * 
		 * }
		 */
		if (this.processoBloqueado.contains(nomeProcesso)) {
			this.aRetomar.add(nomeProcesso);
		} else {
			throw new EscalonadorException();
		}

	}

	public TipoEscalonador escalonadorRoundRobin() {
		return TipoEscalonador.RoundRobin;
	}
	
	
	
	

	public void adicionarProcessoTempoFixo(String string, int duracao) {
		if (listaProcesso.contains(string) || string == null) {
			throw new EscalonadorException();
		}
		if (duracao < 1) {
			throw new EscalonadorException();
		}

		listaProcesso.add(string);
		this.controle = duracao;
		if (tick == 0) {
			ProcessosComTick0(string, duracao);

		} else {
			ProcessosMeioDaFila(string, duracao);
		}

	}

	protected void ProcessosComTick0(String string, int duracao) {
		if (this.controle > this.tempo) {
			this.tempo = this.controle;
			this.Processo1 = string;
			tempoPrafinalizar = duracao;
		}
		if (controle < tempo) {
			this.Processo2 = string;
			controle = duracao;
			tempoPrafinalizar = duracao;

		} else {
			Processo3 = string;
			sobra = duracao;
		}

	}

	protected void ProcessosMeioDaFila(String string, int duracao) {
		variavel += 1;
		if (this.controle > 2) {

			tempo = controle;
			this.Processo3 = string;
			sobra = duracao;

		} else {
			this.Processo1 = string;
			tempo = duracao;
			if (tick == 1) {
				listaProcesso.poll();
				listaProcesso.add(Processo3);
					
				}
			
			}

		}
	}


